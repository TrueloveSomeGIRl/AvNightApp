package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.mode.bean.UpdateApp

class MainRepository {
    suspend fun checkUpdateApp(): AvNightResponse<UpdateApp> {
        return AvNightRetrofitClient.service.checkUpdateApp()
    }

    suspend fun logout(token:String): AvNightResponse<String> {
        return AvNightRetrofitClient.service.logout(token)
    }
}