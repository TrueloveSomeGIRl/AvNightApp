package com.cxw.avnight

import android.app.Application
import android.app.Notification
import android.content.Context
import android.util.Log
import com.baidu.mobstat.MtjConfig
import com.baidu.mobstat.StatService
import com.cxw.avnight.state.EmptyCallback
import com.cxw.avnight.state.ErrorCallback
import com.cxw.avnight.state.LoadingCallback
import com.kingja.loadsir.core.LoadSir

import kotlin.properties.Delegates
import com.cxw.avnight.weight.MediaLoader

import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.yanzhenjie.album.AlbumConfig
import com.yanzhenjie.album.Album
import java.util.*
import com.umeng.message.entity.UMessage
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler


class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()   //委托属性检查为空
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        initUM()

        StatService.autoTrace(this, true, false)

        StatService.setOn(this, StatService.JAVA_EXCEPTION_LOG)  //仅收集java crash，flag = StatService.JAVA_EXCEPTION_LOG
        StatService.setSessionTimeOut(300)  //设置应用进入后台再回到前台为同一次启动的最大间隔时间
        StatService.setDebugOn(false)
        StatService.setFeedTrack(MtjConfig.FeedTrackStrategy.TRACK_SINGLE)

        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(MediaLoader())
                        .setLocale(Locale.getDefault())
                        .build()
        )
        LoadSir.beginBuilder()
                .addCallback(ErrorCallback())
                .addCallback(EmptyCallback())
                .addCallback(LoadingCallback())
                .setDefaultCallback(
                        LoadingCallback::class.java
                )
                .commit()
    }

    private fun initUM() {
        UMConfigure.init(
                this,
                "5dd3aaf63fc195544a000d84",
                "zml",
                UMConfigure.DEVICE_TYPE_PHONE,
                "1d0fbfeb4d8b5ff47d48372c80e1a730"
        )

        val mPushAgent = PushAgent.getInstance(this)

        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {

            }

            override fun onFailure(s: String, s1: String) {

            }
        })
        val notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun openActivity(p0: Context?, p1: UMessage?) {
                super.openActivity(p0, p1)

            }

            override fun openUrl(p0: Context?, p1: UMessage?) {
                super.openUrl(p0, p1)

            }

            override fun launchApp(p0: Context?, p1: UMessage?) {
                super.launchApp(p0, p1)

            }
        }
        mPushAgent.notificationClickHandler = notificationClickHandler


    }
}