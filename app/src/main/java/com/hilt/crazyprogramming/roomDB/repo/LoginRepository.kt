package com.hilt.crazyprogramming.roomDB.repo

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.hilt.crazyprogramming.roomDB.db.LoginDatabase
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class LoginRepository {

    companion object {
        var loginDatabase: LoginDatabase? = null

        var loginModel: LiveData<LoginUser>? = null
        var loginAllUsers: LiveData<List<LoginUser>>? = null
        var loginUserList: List<LoginUser>? = null

        private fun initializeDB(context: Context): LoginDatabase {
            return LoginDatabase.getDataBaseClient(context)
        }

        fun insertData(context: Context, username: String, password: String, comment: String, image: ByteArray) {
            loginDatabase = initializeDB(context)

            var userInfo = LoginUser(username, password, comment, image)
            loginDatabase!!.loginUserDao().insertLoginData(userInfo)

            /*CoroutineScope(Main).launch {
                var userInfo = LoginUser(username, password, comment, image)
                loginDatabase!!.loginUserDao().insertLoginData(userInfo)
            }*/
        }

        fun getLoginDetails(context: Context, username: String): LiveData<LoginUser>? {
            loginDatabase = initializeDB(context)

            loginModel = loginDatabase!!.loginUserDao().getLoginDetails(username)
            return loginModel
        }

        /*fun getUserListsRepo(context: Context): LiveData<List<LoginUser>>? {
            loginDatabase = initializeDB(context)

            loginAllUsers = loginDatabase!!.loginUserDao().getUserLists()
            return loginAllUsers
        }

        fun deleteUserRepo(context: Context, loginUser: LoginUser) {
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginUserDao().deleteUser(loginUser)
        }

        fun deleteUserListsRepo(context: Context) {
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginUserDao().deleteUserList()
        }
    }*/

        fun getUserListsRepo(context: Context): List<LoginUser>? {
            loginDatabase = initializeDB(context)

            loginUserList = loginDatabase!!.loginUserDao().getUserLists()
            return loginUserList
        }

        fun deleteUserRepo(context: Context, loginUser: LoginUser) {
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginUserDao().deleteUser(loginUser)
        }

        fun deleteUserListsRepo(context: Context) {
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginUserDao().deleteUserList()
        }
    }
}