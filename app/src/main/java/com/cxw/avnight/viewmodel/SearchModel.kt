package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.repository.CommentsRepository
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.repository.SearchRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

class SearchModel : BaseViewModel() {
    private val repository by lazy { SearchRepository() }

    val mSearchActorInfo: MutableLiveData<Result<ActorInfo>> = MutableLiveData()


    fun searchActor(name: String, page: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.searchActor(name, page, pageSize)
            }
            executeResponse(result, { mSearchActorInfo.value = result.data }, {})
        }
    }
}