package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.repository.CommentsRepository
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

class CommentsModel : BaseViewModel() {
    private val repository by lazy { CommentsRepository() }
    val mComments: MutableLiveData<Result<Comments>> = MutableLiveData()
    val mReplyComments: MutableLiveData<ChildComment> = MutableLiveData()

    fun getComments(id: Int, type: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getComments(id, type, pageSize)
            }
            executeResponse(result, { mComments.value = result.data }, {})
        }
    }

    fun saveReplyComment(replyCommentsBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.saveReplyComment(replyCommentsBody)
            }
            executeResponse(result, { mReplyComments.value = result.data }, {})
        }
    }
}