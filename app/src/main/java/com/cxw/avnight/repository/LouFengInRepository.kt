package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.mode.bean.AvNightResponse


class LouFengInRepository {
    suspend fun getActorInfo(type:Int,page: Int, pageSize: Int): AvNightResponse<Result<ActorInfo>> {
        return AvNightRetrofitClient.service.getActorInfo(type,page, pageSize)
    }
}