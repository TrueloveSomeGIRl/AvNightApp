package com.cxw.avnight.mode.Exception


import com.cxw.avnight.mode.bean.AvNightResponse

class ServerException : RuntimeException {
    // 异常处理，为速度，不必要设置getter和setter
    var code: Int = 0
    override var message: String? = null
    private lateinit var httpResult: AvNightResponse<*>

    constructor(message: String, code: Int, httpResult: AvNightResponse<*>) : super(message) {
        this.code = code
        this.message = message
        this.httpResult = httpResult
    }

    constructor(e: Throwable, message: String?, code: Int) : super(e) {
        this.code = code
        this.message = message ?: e.message
    }
}