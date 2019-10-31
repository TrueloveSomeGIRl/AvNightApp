package com.cxw.avnight.mode.Api

import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Comments


import retrofit2.http.*
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Multipart


interface AvNightService {

    companion object {
        const val BASE_URL = "http://192.168.0.194:8081/avNight/v1/api/"
    }

    @GET("findVerificationActor/{type}/{pageSize}")
    suspend fun getActorInfo(@Path("type") type: Int, @Path("pageSize") pageSize: Int): AvNightResponse<List<ActorInfo>>

    @GET("selectComments/{id}/{type}/{pageSize}")
    suspend fun getComments(@Path("id") id: Int, @Path("type") type: Int, @Path("pageSize") pageSize: Int): AvNightResponse<List<Comments>>


    @Multipart
    @POST("UploadServer")
    suspend fun uploadActorInfo(@Part parts: List<MultipartBody.Part>, @Part("info") info: String): AvNightResponse<String>
}
