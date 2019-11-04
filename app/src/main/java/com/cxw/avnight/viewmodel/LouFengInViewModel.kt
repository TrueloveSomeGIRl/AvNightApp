package com.cxw.avnight.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.ActorInfoResult
import com.cxw.avnight.repository.LouFengInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LouFengInViewModel : BaseViewModel(),LifecycleObserver{
    private val repository by lazy { LouFengInRepository() }
    val mActorInfo: MutableLiveData<ActorInfoResult> = MutableLiveData()

    fun getActorInfo(type:Int,page: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getActorInfo(type,page, pageSize)
            }
            executeResponse(result, { mActorInfo.value = result.data }, {})
        }
    }
}

