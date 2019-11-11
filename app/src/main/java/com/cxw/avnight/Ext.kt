package com.cxw.avnight



import com.cxw.avnight.mode.bean.AvNightResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope


suspend fun executeResponse(response: AvNightResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errCode == -5) errorBlock()
        else successBlock()
    }
}

