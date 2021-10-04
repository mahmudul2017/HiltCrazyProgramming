package com.hilt.crazyprogramming.coroutines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.utlis.showErrorToast
import com.hilt.crazyprogramming.utlis.showSuccessToast
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoroutinesFragment : Fragment() {

    private var TAG = "CoroutinesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coroutines, container, false)

        // ---------- Asynchronous function calling
        /* viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            timeCounter1()
        } */

        /* GlobalScope.launch {
            timeCounter1()
            timeCounter2()
        } */

        /* lifecycleScope.launch {
            timeCounter1()
            timeCounter2()
        } */

        // ----------- Synchronous function calling
        lifecycleScope.launch(Dispatchers.Main) {
            var timeCounteMilis = measureTimeMillis {
                async {
                    timerCounter1()
                    showSuccessToast(requireContext(), "Timer 1 Complete !!")
                }
                async {
                    timerCounter2()
                    showSuccessToast(requireContext(), "Timer 2 Complete !!")
                }
            }
            Log.d(TAG, "time count in millis $timeCounteMilis")
        }

        return view
    }

    private suspend fun timerCounter2() {
        withContext(Dispatchers.IO) {
            for (i in 0..14) {
                delay(1000)
                Log.d(TAG, "counter 2 called $i")
            }
        }
    }

    private suspend fun timerCounter1() {
        withContext(Dispatchers.IO) {
            for (i in 0..14) {
                delay(1000)
                Log.d(TAG, "counter 1 called $i")
            }
        }
    }

    private suspend fun timeCounter2() {
        withContext(Dispatchers.IO) {
            for (i in 0..14) {
                delay(1000)
                Log.d(TAG, "counter 2 called $i")
            }
        }
    }

    private suspend fun timeCounter1() {
        withContext(Dispatchers.IO) {
            for (i in 0..14) {
                delay(1000)
                Log.d(TAG, "counter 1 called $i")
            }
        }
    }
}