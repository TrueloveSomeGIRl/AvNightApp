package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.mode.bean.UpdateApp
import com.cxw.avnight.repository.CommentsRepository
import com.cxw.avnight.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainViewModel : BaseViewModel() {
    private val repository by lazy { MainRepository() }

    val mUpdateApp: MutableLiveData<UpdateApp> = MutableLiveData()
    val loginOut: MutableLiveData<String> = MutableLiveData()
    fun checkUpdateApp() {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.checkUpdateApp()
            }
            executeResponse(result, { mUpdateApp.value = result.data }, {})
        }
    }

    fun loginOut(token:String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.logout(token)
            }
            executeResponse(result, { loginOut.value = result.data }, {})
        }
    }
}