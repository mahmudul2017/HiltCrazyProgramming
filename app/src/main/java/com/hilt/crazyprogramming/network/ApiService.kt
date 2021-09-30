package com.hilt.crazyprogramming.network

import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.model.postById.PostById
import com.hilt.crazyprogramming.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/users")
    suspend fun getUser() : Response<List<User>>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int) : Response<PostById>

    @POST("/posts")
    suspend fun postUser(@Body postUser: PostUser) : Response<PostUser>
}