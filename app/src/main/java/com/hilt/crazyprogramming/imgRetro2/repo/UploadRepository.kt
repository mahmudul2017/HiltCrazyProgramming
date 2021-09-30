package com.hilt.crazyprogramming.imgRetro2.repo

import com.hilt.crazyprogramming.dInjector.ApiServiceImpl
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UploadRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    suspend fun postUserRepo(postUser: PostUser): Response<PostUser> {
        return withContext(Dispatchers.IO) {
            apiServiceImpl.postUserImpl(postUser)
        }
    }
}