package com.hilt.crazyprogramming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hilt.crazyprogramming.session.SharedPrefSession
import com.hilt.crazyprogramming.utlis.PREF_USER_NAME
import com.hilt.crazyprogramming.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var sharedPrefSession: SharedPrefSession
    private lateinit var prefUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefUser = sharedPrefSession.getPrefUser().toString()
        Log.d("MainActivity", "shared pref outside $prefUser")

        if (prefUser.isNullOrEmpty()) {
            Log.d("activity", "Activity Called")
            userViewModel.getViewModelUser().observe(this@MainActivity, Observer {
                Log.d("MainActivity", "user api call: ${it[0].name}")

                sharedPrefSession.savePrefUser(it[0].name)
            })
        } else {
            Log.d("MainActivity", "shared pref call $prefUser")
            sharedPrefSession.removePrefUser()
        }
    }
}