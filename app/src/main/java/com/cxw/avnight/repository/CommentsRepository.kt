package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient

import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result
import okhttp3.RequestBody

class CommentsRepository {

    suspend fun getComments(id: Int, type: Int, pageSize: Int): AvNightResponse<Result<Comments>> {
        return AvNightRetrofitClient.service.getComments(id, type, pageSize)
    }


    suspend fun saveReplyComment(replyCommentsBody: RequestBody): AvNightResponse<ChildComment> {
        return AvNightRetrofitClient.service.saveReplyComment(replyCommentsBody)
    }

    suspend fun addActorComment(addActorCommentBody: RequestBody): AvNightResponse<Comments> {
        return AvNightRetrofitClient.service.addActorComment(addActorCommentBody)
    }
}