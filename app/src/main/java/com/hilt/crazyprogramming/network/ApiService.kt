package com.hilt.crazyprogramming.network

import com.google.gson.JsonObject
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.imgRetro2.model.PostUserResponse
import com.hilt.crazyprogramming.model.postById.PostById
import com.hilt.crazyprogramming.model.user.User
import retrofit2.Response
import retrofit2.http.*
import okhttp3.RequestBody

import okhttp3.MultipartBody

import okhttp3.ResponseBody
import retrofit2.Call


interface ApiService {
    @GET("/users")
    suspend fun getUser() : Response<List<User>>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int) : Response<PostById>

    @POST("/posts")
    suspend fun postUser(@Body jsonObject: JsonObject) : Response<PostUserResponse>

    @Multipart
    @POST("/posts")
    suspend fun postImage(@Part image: MultipartBody.Part?, @Part("name") name: RequestBody?): Call<ResponseBody?>?
}