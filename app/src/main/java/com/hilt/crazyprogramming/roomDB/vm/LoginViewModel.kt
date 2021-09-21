package com.hilt.crazyprogramming.roomDB.vm

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import com.hilt.crazyprogramming.roomDB.repo.LoginRepository

class LoginViewModel: ViewModel() {
    private var liveDataLogin: LiveData<LoginUser>? = null
    private var liveDataUserList: LiveData<List<LoginUser>>? = null

    fun insertDataVM(context: Context, username: String, password: String, comment: String, image: Bitmap) {
        LoginRepository.insertData(context, username, password, comment, image)
    }

    fun getLoginDetailsVM(context: Context, username: String): LiveData<LoginUser>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

    fun getUserListsVM(context: Context): LiveData<List<LoginUser>>? {
        liveDataUserList = LoginRepository.getUserListsRepo(context)
        return liveDataUserList
    }
}