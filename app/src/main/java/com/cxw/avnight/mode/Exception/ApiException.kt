package com.cxw.avnight.mode.Exception

class ApiException : RuntimeException {
    // 异常处理，为速度，不必要设置getter和setter
    var code: Int = 0
    override var message: String? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
        this.message = throwable.message
        throwable.printStackTrace()
    }

    constructor(status: Int, message: String) : super(message) {
        this.message = message
        this.code = status
    }
}