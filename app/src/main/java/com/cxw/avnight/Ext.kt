package com.cxw.avnight


import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.cxw.avnight.mode.Exception.ApiException
import com.cxw.avnight.mode.bean.AvNightResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope


suspend fun executeResponse(
    response: AvNightResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
    errorBlock: suspend CoroutineScope.() -> Unit
) {
    coroutineScope {
        if (response.errCode != 200)   // 这里强制一点  不是200 视为错误  所提这里后台一定要商量好 什么时候返回200  不然会GG
            throw ApiException(response.errCode, response.errMsg)
   //     errorBlock()
        else
        successBlock()
    }
}


