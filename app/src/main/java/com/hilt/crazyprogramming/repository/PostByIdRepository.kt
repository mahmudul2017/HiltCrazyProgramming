package com.hilt.crazyprogramming.repository

import com.hilt.crazyprogramming.dInjector.ApiServiceImpl
import com.hilt.crazyprogramming.model.postById.PostById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PostByIdRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    suspend fun getPostByIdRepo(postId: Int): Response<PostById> {
        return withContext(Dispatchers.IO) {
            apiServiceImpl.getPostByIdImpl(postId)
        }
    }
}