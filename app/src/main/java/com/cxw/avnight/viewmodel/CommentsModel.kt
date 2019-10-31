package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.repository.CommentsRepository
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse

import com.cxw.avnight.mode.bean.Comments

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsModel : BaseViewModel() {
    private val repository by lazy { CommentsRepository() }
     val mComments: MutableLiveData<List<Comments>> = MutableLiveData()

    fun getComments(id: Int, type: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getComments(id, type, pageSize)
            }
            executeResponse(result, { mComments.value = result.data }, {})
        }
    }
}