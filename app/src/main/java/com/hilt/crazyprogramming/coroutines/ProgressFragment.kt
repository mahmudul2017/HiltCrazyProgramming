package com.hilt.crazyprogramming.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.service.ProgressService
import kotlinx.android.synthetic.main.fragment_progress.*
import kotlinx.android.synthetic.main.fragment_progress.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressFragment : Fragment() {
    private var isProgress = false

    private lateinit var brcastReceiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        view.btnStart.setOnClickListener {
            /* viewLifecycleOwner.lifecycleScope.launch {
                startProgress()
            } */

            var intent = Intent(requireContext(), ProgressService::class.java)
            requireContext().startService(intent)
        }

        view.btnStop.setOnClickListener {
            isProgress = true
        }

        brcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val progressValue = intent?.getIntExtra("progressValue", 0)
                progressBar.progress = progressValue!!
                view.tvProgressValue.text = "$progressValue %"

                /*for (i in 1..100) {
                    lifecycleScope.launch {
                        delay(1000)
                        progressBar.progress = i
                    }
                }*/
                Log.d("ProgressFragment", "$progressValue")
            }
        }

        return view
    }

    private suspend fun startProgress() {
        for (i in 1..100) {
            delay(100)
            progressBar.progress = i
        }
    }

    override fun onResume() {
        intentFilter = IntentFilter(ProgressService.MY_ACTION)
        requireContext().registerReceiver(brcastReceiver, intentFilter)
        super.onResume()
    }

    override fun onDestroy() {
        requireContext().unregisterReceiver(brcastReceiver)
        super.onDestroy()
    }
}