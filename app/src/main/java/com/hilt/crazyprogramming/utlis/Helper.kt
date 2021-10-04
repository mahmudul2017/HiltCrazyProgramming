package com.hilt.crazyprogramming.utlis

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.hilt.crazyprogramming.R
import kotlinx.android.synthetic.main.toast_custom_error.view.*
import kotlinx.android.synthetic.main.toast_custom_green.view.*
import kotlinx.android.synthetic.main.toast_custom_warning.view.*

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun showErrorToast(context: Context, message: String) {
    val parent: ViewGroup? = null
    val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val toastView = inflater.inflate(R.layout.toast_custom_error, parent)
    toastView.errorMessage.text = message
    toast.view = toastView
    toast.show()
}

fun showSuccessToast(context: Context, message: String) {
    val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val toastView = inflater.inflate(R.layout.toast_custom_green, null)
    toastView.successMessage.text = message
    toast.view = toastView
    toast.show()
}

fun showWarningToast(context: Context, message: String) {
    val parent: ViewGroup? = null
    val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val toastView = inflater.inflate(R.layout.toast_custom_warning, parent)
    toastView.warningMessage.text = message
    toast.view = toastView
    toast.show()
}