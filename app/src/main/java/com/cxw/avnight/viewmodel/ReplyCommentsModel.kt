package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.repository.CommentsRepository
import com.cxw.avnight.repository.ReplyCommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

class ReplyCommentsModel : BaseViewModel() {
    private val repository by lazy { ReplyCommentsRepository() }
    private val replyRepository by lazy { CommentsRepository() }
    val mReplyComments: MutableLiveData<Result<ChildComment>> = MutableLiveData()
    val mSaveReplyComments: MutableLiveData<ChildComment> = MutableLiveData()
    fun getSelectByIdReply(id: Int, page: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getSelectByIdReply(id, page, pageSize)
            }
            executeResponse(result, { mReplyComments.value = result.data }, {})
        }
    }

    fun saveReplyComment(replyCommentsBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                replyRepository.saveReplyComment(replyCommentsBody)
            }
            executeResponse(result, { mSaveReplyComments.value = result.data }, {})
        }
    }
}