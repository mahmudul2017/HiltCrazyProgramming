package com.hilt.crazyprogramming.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.session.SharedPrefSession
import com.hilt.crazyprogramming.session.SharedPrefVMSession
import com.hilt.crazyprogramming.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var sharedPrefSession: SharedPrefSession

    @Inject
    lateinit var sharedPrefVMSession: SharedPrefVMSession

    private lateinit var prefUser: String
    private lateinit var prefVmValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefUser = sharedPrefSession.getPrefUser().toString()
        prefVmValue = sharedPrefVMSession.getPrefVM().toString()
        Log.d("MainActivity", "shared pref outside $prefUser : $prefVmValue")

        if (prefUser.isNullOrEmpty()) {
            Log.d("activity", "Activity Called")
            userViewModel.getViewModelUser().observe(this@UserActivity, Observer {
                Log.d("MainActivity", "user api call: ${it[0].name}")

                sharedPrefSession.savePrefUser(it[0].name)
            })
        } else {
            Log.d("MainActivity", "shared pref call $prefUser")
            sharedPrefSession.removePrefUser()
        }

        tvGoToPost.setOnClickListener {
            startActivity(Intent(this@UserActivity, PostByIdActivity::class.java))
        }
    }
}