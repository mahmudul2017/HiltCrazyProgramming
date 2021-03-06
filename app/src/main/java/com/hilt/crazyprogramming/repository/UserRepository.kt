package com.hilt.crazyprogramming.repository

import com.hilt.crazyprogramming.dInjector.ApiServiceImpl
import com.hilt.crazyprogramming.model.user.User
import com.hilt.crazyprogramming.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserRepo(): Response<List<User>> {
        return withContext(Dispatchers.IO) {
            apiService.getUser()
        }
    }
}