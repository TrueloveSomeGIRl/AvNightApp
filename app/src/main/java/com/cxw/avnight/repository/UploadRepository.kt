package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.AvNightResponse

import okhttp3.MultipartBody

class UploadRepository {
    suspend fun getUpload(parts: List<MultipartBody.Part>, info: String): AvNightResponse<String> {
        return AvNightRetrofitClient.service.uploadActorInfo(parts, info)
    }

}