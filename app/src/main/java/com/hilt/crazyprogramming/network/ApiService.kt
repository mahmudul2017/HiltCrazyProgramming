package com.hilt.crazyprogramming.network

import com.hilt.crazyprogramming.model.user.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getUser() : Response<List<User>>
}