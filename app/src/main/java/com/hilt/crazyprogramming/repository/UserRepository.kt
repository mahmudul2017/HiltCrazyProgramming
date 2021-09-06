package com.hilt.crazyprogramming.repository

import com.hilt.crazyprogramming.dInjector.ApiServiceImpl
import com.hilt.crazyprogramming.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    suspend fun getUserRepo(): Response<List<User>> {
        return withContext(Dispatchers.IO) {
            apiServiceImpl.getUserImpl()
        }
    }
}