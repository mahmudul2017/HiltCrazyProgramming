package com.hilt.crazyprogramming.utlis

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hilt.crazyprogramming.R

open class BaseViewModel constructor(val context: Application) : ViewModel() {

    val apiCallStatus: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun checkNetworkStatus(application: Application) =
        if (isNetworkAvailable(application)) {
            true
        } else {
            showErrorToast(application, application.getString(R.string.net_error_msg))
            false
        }

    fun checkNetworkStatus() = if (isNetworkAvailable(context)) {
        true
    } else {
        showErrorToast(context, context.getString(R.string.net_error_msg))
        false
    }

    /*fun onAppExit(preferences: SharedPreferences) {
        preferences.edit().apply {
            putString("LoggedUser", "")
            apply()
        }
    }*/
}