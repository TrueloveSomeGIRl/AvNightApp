package com.cxw.avnight.repository

import com.cxw.avnight.mode.Api.AvNightRetrofitClient
import com.cxw.avnight.mode.bean.AvNightResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository {
    suspend fun getUpload(parts: List<MultipartBody.Part>, info: RequestBody): AvNightResponse<String> {
        return AvNightRetrofitClient.service.uploadActorInfo(parts, info)
    }

}