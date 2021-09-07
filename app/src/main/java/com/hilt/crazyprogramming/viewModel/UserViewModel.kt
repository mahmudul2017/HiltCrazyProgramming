package com.hilt.crazyprogramming.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilt.crazyprogramming.model.user.User
import com.hilt.crazyprogramming.network.ApiEmptyResponse
import com.hilt.crazyprogramming.network.ApiErrorResponse
import com.hilt.crazyprogramming.network.ApiResponse
import com.hilt.crazyprogramming.network.ApiSuccessResponse
import com.hilt.crazyprogramming.repository.UserRepository
import com.hilt.crazyprogramming.utlis.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(@ApplicationContext application: Context, private val userRepository: UserRepository): BaseViewModel(application as Application) {

    fun getViewModelUser(): LiveData<List<User>> {
        val user = MutableLiveData<List<User>>()

        if (checkNetworkStatus()) {
            apiCallStatus.postValue("LOADING")

            viewModelScope.launch {
                when (val apiResponse = ApiResponse.create(userRepository.getUserRepo())) {
                    is ApiSuccessResponse -> {
                        //apiCallStatus.postValue("SUCCESS")
                        user.postValue(apiResponse.body)
                        Log.d("response", apiResponse.toString())
                    }
                    is ApiErrorResponse -> {
                        //apiCallStatus.postValue("ERROR")
                        Log.d("response", apiResponse.toString())
                    }
                    is ApiEmptyResponse -> {
                        //apiCallStatus.postValue("EMPTY")
                        Log.d("response", apiResponse.toString())
                    }
                }
            }
        }
        Log.d("ViewModelUser", user.toString())
        return user
    }
}