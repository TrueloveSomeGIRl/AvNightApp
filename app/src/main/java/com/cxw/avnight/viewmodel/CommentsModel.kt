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
    val mSaveReplyComments: MutableLiveData<ChildComment> = MutableLiveData()
    val mActorComments: MutableLiveData<Comments> = MutableLiveData()

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
            executeResponse(result, { mSaveReplyComments.value = result.data }, {})
        }
    }

    fun addActorComment(addActorCommentBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.addActorComment(addActorCommentBody)
            }
            executeResponse(result, { mActorComments.value = result.data }, {})
        }
    }
}