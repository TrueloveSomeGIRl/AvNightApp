package com.cxw.avnight.base



import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cxw.avnight.mode.Exception.ApiException
import com.cxw.avnight.mode.Exception.ExceptionEngine
import kotlinx.coroutines.*


open class BaseViewModel : ViewModel(), LifecycleObserver {
    val mException: MutableLiveData<ApiException> = MutableLiveData()
    val mLoading: MutableLiveData<Boolean> = MutableLiveData()
    val mRequestSuccess: MutableLiveData<Boolean> = MutableLiveData()

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            mLoading.value = true
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    fun launchOnUITryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }


    private suspend fun tryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean = false
    ) {

        coroutineScope {
            try {
                tryBlock()
                mRequestSuccess.value = true
            } catch (e: Throwable) {             //异常走这里  不区分直接给ExceptionEngine 他判断 抛什么错误
//                if (e !is CancellationException || handleCancellationExceptionManually) {
//                    mException.value = ExceptionEngine.handleException(e)
//                    catchBlock(e)
//                } else {
//                    throw e
//                }
                mException.value = ExceptionEngine.handleException(e)
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }
}

