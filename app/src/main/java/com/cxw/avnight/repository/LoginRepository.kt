package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.*

import okhttp3.RequestBody

class LoginRepository {
    suspend fun login(userBody: RequestBody): AvNightResponse<LoginEntity> {
        return AvNightRetrofitClient.service.login(userBody)
    }

    suspend fun registered(userBody: RequestBody): AvNightResponse<RegisteredEntity> {
        return AvNightRetrofitClient.service.registered(userBody)
    }
    suspend fun getEmailCode(email:String): AvNightResponse<EmailEntity> {
        return AvNightRetrofitClient.service.getEmailCode(email)
    }
}