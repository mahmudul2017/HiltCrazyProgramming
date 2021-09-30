package com.hilt.crazyprogramming.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hilt.crazyprogramming.roomDB.model.LoginUser

@Dao
interface LoginUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginData(loginUser: LoginUser)

    @Query("SELECT * FROM HiltDatabase WHERE username = :username")
    fun getLoginDetails(username: String): LiveData<LoginUser>

    /*@Query("SELECT * FROM HiltLogIn")
    fun getUserLists(): LiveData<List<LoginUser>>*/

    @Query("SELECT * FROM HiltDatabase")
    fun getUserLists(): List<LoginUser>

    @Delete
    fun deleteUser(loginUser: LoginUser)

    @Query("DELETE FROM HiltDatabase")
    fun deleteUserList()
}