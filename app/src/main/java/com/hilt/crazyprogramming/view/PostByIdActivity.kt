package com.hilt.crazyprogramming.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.viewModel.PostByIdViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_postbyid.*

@AndroidEntryPoint
class PostByIdActivity : AppCompatActivity() {
    private val postByIdViewModel: PostByIdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postbyid)

        btnPost.setOnClickListener {
            val postId = edtPostId.text.toString()
            postByIdViewModel.getViewModelPostById(postId.toInt()).observe(this@PostByIdActivity, Observer {
                Log.d("PostIdValue", it.title)
            })
        }
    }
}