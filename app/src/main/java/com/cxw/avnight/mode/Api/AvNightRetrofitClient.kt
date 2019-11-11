package com.cxw.avnight.mode.Api

import android.util.Log
import com.cxw.avnight.App
import com.cxw.avnight.util.NetWorkUtils
import com.cxw.avnight.util.SPUtil
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File


object AvNightRetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(AvNightService::class.java, AvNightService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            .addInterceptor { chain ->
                //addInterceptor是添加在与服务器连接之前和之后
                // addNetworkInterceptor是添加在与服务器建立连接和发起请求的之间

                var request = chain.request()
                if (SPUtil.getValue("token","").toString().isNotEmpty()){
                    request = request.newBuilder().header("token", SPUtil.getValue("token","").toString()).build()
                }
                //  拦截一个请求在网络不可用的时候使用CacheControl.FORCE_CACHE
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)   //仅仅使用缓存
                        .build()
                }
                val response = chain.proceed(request)

                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }

                response
            }
    }
}