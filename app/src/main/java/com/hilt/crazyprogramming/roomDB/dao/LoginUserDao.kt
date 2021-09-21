package com.hilt.crazyprogramming.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hilt.crazyprogramming.roomDB.model.LoginUser

@Dao
interface LoginUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginData(loginUser: LoginUser)

    @Query("SELECT * FROM HiltLogIn WHERE username = :username")
    fun getLoginDetails(username: String): LiveData<LoginUser>

    @Query("SELECT * FROM HiltLogIn")
    fun getUserLists(): LiveData<List<LoginUser>>

    @Delete
    fun deleteUser(loginUser: LoginUser)

    @Query("DELETE FROM HiltLogIn")
    fun deleteUserList()
}