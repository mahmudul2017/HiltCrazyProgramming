package com.hilt.crazyprogramming.imgRetro2.repo

import com.google.gson.JsonObject
import com.hilt.crazyprogramming.dInjector.ApiServiceImpl
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.imgRetro2.model.PostUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UploadRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    suspend fun postUserRepo(postUser: PostUser): Response<PostUserResponse> {
        val userObject = JsonObject().apply {
            addProperty("name", postUser.name)
            addProperty("phone", postUser.phone)
            addProperty("gender", postUser.gender)
        }

        return withContext(Dispatchers.IO) {
            apiServiceImpl.postUserImpl(userObject)
        }
    }
}