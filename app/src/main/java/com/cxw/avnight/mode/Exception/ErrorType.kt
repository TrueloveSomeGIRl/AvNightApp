package com.cxw.avnight.mode.Exception

/**
 * 约定异常
 */

interface ErrorType {
    companion object {

        //---------- ExceptionEngine使用 通过ExceptionEngin指定的 -------

        /**
         * 解析错误 JsonParseException JSONException ParseException
         */
        const val PARSE_ERROR = 1001
        /**
         * 网络超时和链接异常 ConnectException SocketTimeoutException ConnectTimeoutException UnknownHostException
         */
        const  val NETWORK_ERROR = 1002
        /**
         * 协议出错  HttpException
         */
        const  val HTTP_ERROR = 1003
        /**
         * 未知错误
         */
        const  val UNKNOWN_ERROR = 1000

        //----------服务器返回的 异常状态,服务器不同值不同
        /**
         * 服务器返回de非SUCCESS的错误
         */

        const  val ERROR = -1

        /**
         * 正常
         */
        const  val SUCCESS = 200
    }


}