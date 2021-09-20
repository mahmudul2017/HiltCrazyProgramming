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
import java.lang.Exception


class SplashActivity : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var comment: String

    private lateinit var context: Context
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var alertProfileDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        context = this@SplashActivity
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        imgPicker.setOnClickListener {
            chooseProfilePicture()
        }

        btnLogin.setOnClickListener {
            username = edtUserName.text.toString().trim()
            password = edtPassword.text.toString().trim()
            comment = edtComment.text.toString().trim()

            when {
                username.isNullOrEmpty() -> {
                    edtUserName.error = "Please enter the username"
                }
                password.isNullOrEmpty() -> {
                    edtPassword.error = "Please enter the password"
                }
                else -> {
                    loginViewModel.insertDataVM(context, username, password, comment)
                    showErrorToast(this, "Inserted Successfully")
                }
            }
        }

        btnUserList.setOnClickListener {
            loginViewModel.getUserListsVM(this)?.observe(this, Observer {
                Log.d("userData", it.toString())
            })
        }

        // get data from db when using name search
        /*btnReadLogin.setOnClickListener {
            username = txtUsername.text.toString().trim()

            loginViewModel.getLoginDetailsVM(context, username)!!.observe(this, Observer {
                if (it == null) {
                    lblReadResponse.text = "Data Not Found"
                    lblUseraname.text = "- - -"
                    lblPassword.text = "- - -"
                }
                else {
                    lblUseraname.text = it.userName
                    lblPassword.text = it.password

                    lblReadResponse.text = "Data Found Successfully"
                }
            })
        }*/
    }

    private fun chooseProfilePicture() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@SplashActivity)
        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_profile_pic, null)
        builder.setCancelable(false)
        builder.setView(dialogView)

        val imgCamera: ImageView = dialogView.findViewById(R.id.imgCamera)
        val imgGallery: ImageView = dialogView.findViewById(R.id.imgGallery)
        val imgClose: ImageView = dialogView.findViewById(R.id.imgClose)

        imgClose.setOnClickListener {
            alertProfileDialog.cancel()
        }

        imgCamera.setOnClickListener {
            if (checkAndRequestPermissions()) {
                takePictureFromCamera()
                //alertDialogProfilePicture.cancel()
            }
        }

        imgGallery.setOnClickListener {
            takePictureFromGallery()
            //alertDialogProfilePicture.cancel()
        }

        alertProfileDialog = builder.create()
        alertProfileDialog.show()
    }

    private fun takePictureFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, 1)
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission =
                ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.CAMERA)
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this@SplashActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    20
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            takePictureFromCamera()
        } else {
            Toast.makeText(SplashActivity@this, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private fun takePictureFromCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicture.resolveActivity(packageManager) != null) {
            startActivityForResult(takePicture, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            1 -> if (resultCode === RESULT_OK) {
                alertProfileDialog.cancel()
                val selectedImageUri: Uri? = intent!!.data
                imgProfile.setImageURI(selectedImageUri)
            }
            2 -> if (resultCode === RESULT_OK) {
                alertProfileDialog.cancel()
                val bundle: Bundle? = intent!!.extras
                val bitmapImage = bundle!!["data"] as Bitmap?
                imgProfile.setImageBitmap(bitmapImage)
            }
        }
    }

     private fun uriToBitmapConverter(imageUri: Uri) {
         var bitmap: Bitmap? = null
         val contentResolver = contentResolver
         try {
             bitmap = if (Build.VERSION.SDK_INT < 28) {
                 MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
             } else {
                 val source: ImageDecoder.Source =
                     ImageDecoder.createSource(contentResolver, imageUri)
                 ImageDecoder.decodeBitmap(source)
             }
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }
}