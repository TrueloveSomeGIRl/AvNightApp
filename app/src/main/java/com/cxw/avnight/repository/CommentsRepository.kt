package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient

import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Comments

class CommentsRepository {

    suspend fun getComments(id: Int, type: Int, pageSize: Int): AvNightResponse<List<Comments>> {
        return AvNightRetrofitClient.service.getComments(id, type, pageSize)
    }

}