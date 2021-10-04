package com.hilt.crazyprogramming.splash

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.roomDB.bitmap.BitmapConverter
import com.hilt.crazyprogramming.roomDB.utils.FileUtil
import com.hilt.crazyprogramming.roomDB.vm.LoginViewModel
import com.hilt.crazyprogramming.utlis.showErrorToast
import id.zelory.compressor.Compressor
import id.zelory.compressor.loadBitmap
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

class SplashFragment : Fragment() {
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var comment: String

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var alertProfileDialog: AlertDialog
    private var compressImageFile: File? = null
    private lateinit var galleryImageFile: File
    private var imageBitmap: Bitmap? = null
    var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        imgPicker.setOnClickListener {
            chooseProfilePicture()
        }

        btnLogin.setOnClickListener {
            if (compressImageFile != null) {
                lifecycleScope.launch {
                    galleryImageFile = compressImageSize(compressImageFile!!)
                    saveUserDataToDB(galleryImageFile)
                    Log.d("splash", "btn click $galleryImageFile $compressImageFile")
                }
            } else {
                showErrorToast(requireContext(), "Please select profile image")
            }
        }

        imgDelete.setOnClickListener {
            loginViewModel.deleteUserListsVM(requireContext())
        }

        btnDashBoard.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_dashBoardFragment)
        }

        btnUpload.setOnClickListener {
            // Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_uploadFragment)
        }

        // get data from db when using name search
        /* btnReadLogin.setOnClickListener {
            username = txtUsername.text.toString().trim()

            loginViewModel.getLoginDetailsVM(context, username)!!.observe(this, Observer {
                if (it == null) {
                    lblReadResponse.text = "Data Not Found"
                    lblUsername.text = "- - -"
                    lblPassword.text = "- - -"
                 } else {
                    lblUsername.text = it.userName
                    lblPassword.text = it.password

                    lblReadResponse.text = "Data Found Successfully"
                }
            })
        } */
    }

    private suspend fun compressImageSize(file: File): File {
        return withContext(Dispatchers.Main) {
            Compressor.compress(requireContext(), file)
        }
    }

    private fun saveUserDataToDB(compressImageFile: File) {
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
                // val image = (imgProfile.drawable as BitmapDrawable).bitmap
                val image = BitmapFactory.decodeFile(compressImageFile.toString())

                val stream = ByteArrayOutputStream()
                image!!.compress(Bitmap.CompressFormat.JPEG, 10, stream)
                val imageInByte: ByteArray = stream.toByteArray()
                Log.d("image", "$image")

                if (imageInByte != null) {
                    loginViewModel.insertDataVM(
                        requireContext(),
                        username,
                        password,
                        comment,
                        imageInByte!!
                    )
                    d("userData", "$username $password ${image.byteCount}")
                    showErrorToast(requireContext(), "Inserted Successfully")
                } else {
                    showErrorToast(requireContext(), "Please select profile image")
                }
            }
        }
    }

    private fun chooseProfilePicture() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
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
                d("imgCamera", "imgCamera called")
            }
        }

        imgGallery.setOnClickListener {
            takePictureFromGallery()
        }

        alertProfileDialog = builder.create()
        alertProfileDialog.show()
    }

    /* val imageIntent = Intent(Intent.ACTION_GET_CONTENT)
       imageIntent.type = "image/*"
       startActivityForResult(Intent.createChooser(imageIntent, "gallery image"), 1)*/
    */

    private fun takePictureFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, 1)
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission =
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                // this method is called only activity
                /* ActivityCompat.requestPermissions(
                    requireContext() as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    20
                ) */

                // this method is called only fragment
                requestPermissions(
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

        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()
            alertProfileDialog.cancel()
        } else {
            Toast.makeText(requireContext(), "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private fun takePictureFromCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicture.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePicture, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                alertProfileDialog.cancel()
                compressImageFile = FileUtil.from(requireContext(), intent!!.data!!)
                imgProfile.setImageBitmap(loadBitmap(compressImageFile!!))

                // normal way to set image into imageview from gallery
                /* val imageUri: Uri = intent!!.data!!
                imgProfile.setImageURI(imageUri) */
                d("splash", "imgGallery called bitmap $compressImageFile")
            }
            2 -> if (resultCode == RESULT_OK) {
                alertProfileDialog.cancel()
                val bundle: Bundle? = intent!!.extras
                val bitmapImage = bundle!!["data"] as Bitmap?
                imageBitmap = bitmapImage!!
                imgProfile.setImageBitmap(bitmapImage)
                d("img", "imgCamera called")
            }
        }
    }

    private fun uriToBitmapConverter(imageUri: Uri): Bitmap {
        val contentResolver = requireContext().contentResolver

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
        return bitmap!!
    }

    override fun onResume() {
        super.onResume()
        Log.d("SplashFragment", "onDestroy called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SplashFragment", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SplashFragment", "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SplashFragment", "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SplashFragment", "onDestroy called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("SplashFragment", "onDetach called")
    }
}