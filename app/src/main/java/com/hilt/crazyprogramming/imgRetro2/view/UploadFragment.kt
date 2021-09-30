package com.hilt.crazyprogramming.imgRetro2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.imgRetro2.model.PostUser
import com.hilt.crazyprogramming.imgRetro2.vmodel.UploadViewModel
import com.hilt.crazyprogramming.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_upload.*

@AndroidEntryPoint
class UploadFragment : Fragment() {
    private lateinit var userName: String
    private lateinit var phone: String
    private lateinit var gender: String

    private val uploadViewModel: UploadViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = edtName.text.toString()
        phone = edtPhone.text.toString()
        gender = edtGender.text.toString()

        val postUser = PostUser(userName, phone, gender)
        uploadViewModel.postUserViewModel(postUser)
        uploadViewModel.postUserInfo.observe(viewLifecycleOwner, Observer {
            Log.d("UploadFragment", "$it")
        })
    }
}