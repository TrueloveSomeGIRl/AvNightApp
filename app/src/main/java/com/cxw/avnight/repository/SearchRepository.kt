package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.mode.bean.UpdateApp

class SearchRepository {
    suspend fun searchActor(name: String, page: Int, pageSize: Int): AvNightResponse<Result<ActorInfo>> {
        return AvNightRetrofitClient.service.searchActor(name, page, pageSize)
    }


}