package com.cxw.avnight.mode.Api

import com.cxw.avnight.mode.bean.*


import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart


interface AvNightService {

    companion object {
        //const val BASE_URL = "http://47.100.91.50:1996/avNight/v1/api/"
           const val BASE_URL = "http://192.168.1.7:8099/avNight/v1/api/"
    }

    @GET("findVerificationActor/{type}/{page}/{pageSize}")
    suspend fun getActorInfo(@Path("type") type: Int, @Path("page") page: Int, @Path("pageSize") pageSize: Int): AvNightResponse<Result<ActorInfo>>

    @GET("selectComments/{id}/{page}/{pageSize}")
    suspend fun getComments(@Path("id") id: Int, @Path("page") page: Int, @Path("pageSize") pageSize: Int): AvNightResponse<Result<Comments>>

    @GET("email/{email}")
    suspend fun getEmailCode(@Path("email") email: String): AvNightResponse<EmailEntity>

    @POST("login")
    suspend fun login(@Body loginBody: RequestBody): AvNightResponse<LoginEntity>

    @POST("registered")
    suspend fun registered(@Body registeredBody: RequestBody): AvNightResponse<RegisteredEntity>

    @POST("updatePassword")
    suspend fun updatePassword(@Body updatePasswordBody: RequestBody): AvNightResponse<String>

    @Multipart
    @POST("insertActorInfo")
    suspend fun uploadActorInfo(@Part parts: List<MultipartBody.Part>, @Part("Info") infoBody: RequestBody): AvNightResponse<String>


    @GET("selectByIdReply/{id}/{page}/{pageSize}")
    suspend fun getSelectByIdReply(@Path("id") id: Int, @Path("page") page: Int, @Path("pageSize") pageSize: Int): AvNightResponse<Result<ChildComment>>

    @POST("saveReplyComment")
    suspend fun saveReplyComment(@Body replyCommentsBody: RequestBody): AvNightResponse<ChildComment>

    @POST("addActorComment")
    suspend fun addActorComment(@Body addActorCommentBody: RequestBody): AvNightResponse<Comments>



}
