package com.hilt.crazyprogramming.session

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hilt.crazyprogramming.utlis.PREF_SHARED_VMVALUE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefVMSession @Inject constructor(@ApplicationContext context: Context) {
    private val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun savePrefVm(value: String) {
        pref.edit().putString(PREF_SHARED_VMVALUE, value).apply()
    }

    fun getPrefVM(): String? {
        return pref.getString(PREF_SHARED_VMVALUE, "")
    }

    fun removeVM() {
        pref.edit().clear()
    }
}