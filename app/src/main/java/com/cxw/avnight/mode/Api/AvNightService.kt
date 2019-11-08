package com.cxw.avnight.mode.Api

import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.mode.bean.AvNightResponse
import com.cxw.avnight.mode.bean.Comments


import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart


interface AvNightService {

    companion object {
        const val BASE_URL = "http://192.168.1.7:8099/avNight/v1/api/"
    }

    @GET("findVerificationActor/{type}/{page}/{pageSize}")
    suspend fun getActorInfo(@Path("type") type: Int,@Path("page") page: Int, @Path("pageSize") pageSize: Int): AvNightResponse<Result<ActorInfo>>

    @GET("selectComments/{id}/{type}/{pageSize}")
    suspend fun getComments(@Path("id") id: Int, @Path("type") type: Int, @Path("pageSize") pageSize: Int): AvNightResponse<Result<Comments>>


    @Multipart
    @POST("insertActorInfo")
    suspend fun uploadActorInfo(@Part parts: List<MultipartBody.Part>, @Part("Info")  infoBody: RequestBody ): AvNightResponse<String>
}
