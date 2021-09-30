package com.hilt.crazyprogramming.roomDB.vm

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import com.hilt.crazyprogramming.roomDB.repo.LoginRepository

class LoginViewModel: ViewModel() {
    private var liveDataLogin: LiveData<LoginUser>? = null
    var liveDataUserList: MutableLiveData<List<LoginUser>>? = null
    private var liveDataUserLists: List<LoginUser>? = null

    fun insertDataVM(context: Context, username: String, password: String, comment: String, image: ByteArray) {
        LoginRepository.insertData(context, username, password, comment, image)
    }

    fun getLoginDetailsVM(context: Context, username: String): LiveData<LoginUser>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

    /*fun getUserListsVM(context: Context): LiveData<List<LoginUser>>? {
        liveDataUserList = LoginRepository.getUserListsRepo(context)
        return liveDataUserList
    }*/

    fun getUserListsVM(context: Context): List<LoginUser>? {
        liveDataUserLists = LoginRepository.getUserListsRepo(context)
        //liveDataUserList!!.value = liveDataUserLists
        return liveDataUserLists
    }

    fun deleteUserVM(context: Context, loginUser: LoginUser) {
        LoginRepository.deleteUserRepo(context, loginUser)
    }

    fun deleteUserListsVM(context: Context) {
        LoginRepository.deleteUserListsRepo(context)
    }
}