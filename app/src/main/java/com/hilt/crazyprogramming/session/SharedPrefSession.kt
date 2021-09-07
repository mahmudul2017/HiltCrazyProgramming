package com.hilt.crazyprogramming.session

import android.content.SharedPreferences
import com.hilt.crazyprogramming.utlis.PREF_USER_NAME
import javax.inject.Inject

class SharedPrefSession @Inject constructor(private val sharedPreference: SharedPreferences) {
    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    fun getPrefUser(): String? = sharedPreference.getString(PREF_USER_NAME, "")

    fun savePrefUser(userName: String) {
        editor.putString(PREF_USER_NAME, userName)
        editor.apply()
    }

    fun removePrefUser() {
        editor.clear().apply()
    }
}
