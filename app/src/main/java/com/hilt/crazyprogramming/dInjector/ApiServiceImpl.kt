package com.hilt.crazyprogramming.dInjector
import com.hilt.crazyprogramming.model.user.User
import com.hilt.crazyprogramming.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserImpl(): Response<List<User>> = apiService.getUser()
}