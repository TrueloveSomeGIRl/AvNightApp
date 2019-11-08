package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient

import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result

class CommentsRepository {

    suspend fun getComments(id: Int, type: Int, pageSize: Int): AvNightResponse<Result<Comments>> {
        return AvNightRetrofitClient.service.getComments(id, type, pageSize)
    }

}