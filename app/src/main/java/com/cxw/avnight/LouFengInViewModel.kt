package com.cxw.avnight

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.repository.LouFengInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LouFengInViewModel : BaseViewModel(),LifecycleObserver{
    private val repository by lazy { LouFengInRepository() }
    val mActorInfo: MutableLiveData<List<ActorInfo>> = MutableLiveData()

    fun getActorInfo(type: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getActorInfo(type, pageSize)
            }
            executeResponse(result, { mActorInfo.value = result.data }, {})
        }
    }
}

