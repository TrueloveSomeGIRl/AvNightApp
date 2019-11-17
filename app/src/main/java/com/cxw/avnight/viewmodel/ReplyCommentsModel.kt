package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.repository.ReplyCommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReplyCommentsModel : BaseViewModel() {
    private val repository by lazy { ReplyCommentsRepository() }
    val mReplyComments: MutableLiveData<Result<ChildComment>> = MutableLiveData()

    fun getSelectByIdReply(id: Int, page: Int, pageSize: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getSelectByIdReply(id, page, pageSize)
            }
            executeResponse(result, { mReplyComments.value = result.data }, {})
        }
    }
}