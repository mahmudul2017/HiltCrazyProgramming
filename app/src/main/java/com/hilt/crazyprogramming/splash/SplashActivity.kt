package com.hilt.crazyprogramming.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.roomDB.vm.LoginViewModel
import com.hilt.crazyprogramming.utlis.showErrorToast
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.ContentResolver
import android.graphics.ImageDecoder
import android.media.Image
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStop() {
        super.onStop()
        Log.d("SplashActivity", "onStop called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SplashActivity", "onResume called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SplashActivity", "onDestroy called")
    }
}