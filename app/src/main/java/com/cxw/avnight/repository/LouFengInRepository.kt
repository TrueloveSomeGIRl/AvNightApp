package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.ActorInfoResult
import com.cxw.avnight.mode.bean.AvNightResponse


class LouFengInRepository {
    suspend fun getActorInfo(type:Int,page: Int, pageSize: Int): AvNightResponse<ActorInfoResult> {
        return AvNightRetrofitClient.service.getActorInfo(type,page, pageSize)
    }
}