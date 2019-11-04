package com.cxw.avnight.mode.Exception

import android.net.ParseException

import com.google.gson.JsonParseException

import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import retrofit2.HttpException


/**
 * 异常 的 提示转化
 */
object ExceptionEngine {
    //对应HTTP的状态码
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is ApiException) {
            return e
        } else if (e is HttpException) {             //HTTP错误
            ex = ApiException(
                e,
                ErrorType.HTTP_ERROR
            )
            when (e.code()) {
                UNAUTHORIZED -> ex.message = "当前请求需要用户验证"
                FORBIDDEN -> ex.message = "服务器已经理解请求，但是拒绝执行它"
                NOT_FOUND -> ex.message = "服务器异常，请稍后再试"
                REQUEST_TIMEOUT -> ex.message = "请求超时"
                GATEWAY_TIMEOUT -> ex.message =
                    "作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器（URI标识出的服务器，例如HTTP、FTP、LDAP）或者辅助服务器（例如DNS）收到响应"
                INTERNAL_SERVER_ERROR -> ex.message = "服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理"
                BAD_GATEWAY -> ex.message = "作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应"
                SERVICE_UNAVAILABLE -> ex.message = "由于临时的服务器维护或者过载，服务器当前无法处理请求"
                else -> ex.message = "请求网络错误"  //其它均视为网络错误
            }
            return ex
        } else if (e is ServerException) {    //服务器返回的错误   这里该怎么处理呢
            ex = ApiException(
                e,
                if (e.code == -1) ErrorType.ERROR else ErrorType.ERROR
            )
            ex.message = e.message
            return ex
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            ex = ApiException(
                e,
                ErrorType.PARSE_ERROR
            )
            ex.message = "解析错误"            //均视为解析错误
            return ex
        } else if (e is ConnectException || e is SocketTimeoutException || e is ConnectTimeoutException) {
            ex = ApiException(
                e,
                ErrorType.NETWORK_ERROR
            )
            ex.message = "网络异常"  //均视为网络错误
            return ex
        } else if (e is UnknownHostException) {
            ex = ApiException(
                e,
                ErrorType.NETWORK_ERROR
            )
            ex.message = "网络不给力，请稍后再试"
            return ex
        } else {
            ex = ApiException(
                e,
                ErrorType.UNKNOWN_ERROR
            )
            ex.message = "未知错误"          //未知错误
            return ex
        }
    }

}