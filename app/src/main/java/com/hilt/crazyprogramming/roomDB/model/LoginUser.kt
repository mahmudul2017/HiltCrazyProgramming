package com.hilt.crazyprogramming.roomDB.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HiltLogIn")
data class LoginUser(
    @ColumnInfo(name = "username")
    var userName: String?,

    @ColumnInfo(name = "password")
    var password: String?,

    @ColumnInfo(name = "comment")
    var comment: String?,

    @ColumnInfo(name = "userpic")
    var userPic: Bitmap?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}