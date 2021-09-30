package com.hilt.crazyprogramming.imgRetro2.vmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.imgRetro2.repo.UploadRepository
import com.hilt.crazyprogramming.network.ApiEmptyResponse
import com.hilt.crazyprogramming.network.ApiErrorResponse
import com.hilt.crazyprogramming.network.ApiResponse
import com.hilt.crazyprogramming.network.ApiSuccessResponse
import com.hilt.crazyprogramming.utlis.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(@ApplicationContext context: Context, private val uploadRepository: UploadRepository): BaseViewModel(context as Application) {
    val postUserInfo = MutableLiveData<PostUser>()

    fun postUserViewModel(postUser: PostUser): LiveData<PostUser> {
        if (checkNetworkStatus()) {
            apiCallStatus.postValue("LOADING")

            viewModelScope.launch {
                when (val apiResponse = ApiResponse.create(uploadRepository.postUserRepo(postUser))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue("SUCCESS")
                        postUserInfo.postValue(apiResponse.body)
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
        Log.d("PostUserVM", postUserInfo.toString())
        return postUserInfo
    }
}