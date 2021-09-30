package com.hilt.crazyprogramming.dInjector
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.model.postById.PostById
import com.hilt.crazyprogramming.model.user.User
import com.hilt.crazyprogramming.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserImpl(): Response<List<User>> = apiService.getUser()

    suspend fun getPostByIdImpl(postId: Int): Response<PostById> = apiService.getPostById(postId)

    suspend fun postUserImpl(postUser: PostUser): Response<PostUser> = apiService.postUser(postUser)
}