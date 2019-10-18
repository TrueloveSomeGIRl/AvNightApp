package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.AvNightResponse


class
LouFengInRepository {
    suspend fun getActorInfo(type: Int, pageSize: Int): AvNightResponse<List<ActorInfo>> {
        return AvNightRetrofitClient.service.getActorInfo(type, pageSize)
    }

}