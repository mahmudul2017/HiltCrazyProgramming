package com.hilt.crazyprogramming.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hilt.crazyprogramming.model.postById.PostById
import com.hilt.crazyprogramming.model.user.User
import com.hilt.crazyprogramming.network.ApiEmptyResponse
import com.hilt.crazyprogramming.network.ApiErrorResponse
import com.hilt.crazyprogramming.network.ApiResponse
import com.hilt.crazyprogramming.network.ApiSuccessResponse
import com.hilt.crazyprogramming.repository.PostByIdRepository
import com.hilt.crazyprogramming.utlis.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostByIdViewModel @Inject constructor(@ApplicationContext context: Context, private val postByIdRepository: PostByIdRepository): BaseViewModel(context as Application) {

    fun getViewModelPostById(postId: Int): LiveData<PostById> {
        val postIdValue = MutableLiveData<PostById>()

        if (checkNetworkStatus()) {
            apiCallStatus.postValue("LOADING")

            viewModelScope.launch {
                when (val apiResponse = ApiResponse.create(postByIdRepository.getPostByIdRepo(postId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue("SUCCESS")
                        postIdValue.postValue(apiResponse.body)
                        Log.d("response", apiResponse.toString())
                    }
                    is ApiErrorResponse -> {
                        apiCallStatus.postValue("ERROR")
                        Log.d("response", apiResponse.toString())
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue("EMPTY")
                        Log.d("response", apiResponse.toString())
                    }
                }
            }
        }
        Log.d("PostByIdViewModel", postId.toString())
        return postIdValue
    }
}