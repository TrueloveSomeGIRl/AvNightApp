package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Result
import okhttp3.RequestBody


class ReplyCommentsRepository {
    suspend fun getSelectByIdReply(
        id: Int,
        page: Int,
        pageSize: Int
    ): AvNightResponse<Result<ChildComment>> {
        return AvNightRetrofitClient.service.getSelectByIdReply(id, page, pageSize)
    }


}