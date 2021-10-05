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

        // ------------------ Asynchronous function calling -----------------------
        /* viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            timeCounter1()
        } */

        /* GlobalScope.launch {
            timeCounter3()
            timeCounter4()
        } */

        /* lifecycleScope.launch {
            timeCounter3()
            timeCounter4()
        } */

        // --------------------- Synchronous function calling ---------------------
        lifecycleScope.launch(Dispatchers.Main) {
            var timeCounterMillis = measureTimeMillis {
                async {
                    timerCounter1()
                    showSuccessToast(requireContext(), "Timer 1 Complete !!")
                    Log.d(TAG, "######## Timer 1 finished ########")
                }
                async {
                    timerCounter2()
                    showSuccessToast(requireContext(), "Timer 2 Complete !!")
                    Log.d(TAG, "######## Timer 2 finished ########")
                }
            }
            Log.d(TAG, "time count 1 in millis $timeCounterMillis")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            var timeCalculate = measureTimeMillis {
                var counter1 = async {
                    timeCounter3()
                    showErrorToast(requireContext(), "Timer 3 Complete !!")
                    Log.d(TAG, "######## Timer 3 finished ########")
                }
                var counter2 = async {
                    timeCounter4()
                    showErrorToast(requireContext(), "Timer 4 Complete !!")
                    Log.d(TAG, "######## Timer 4 finished ########")
                }

                counter1.await()
                counter2.await()
            }
            Log.d(TAG, "time count 2 in millis $timeCalculate")
        }

        return view
    }

    private suspend fun timerCounter1() {
        withContext(Dispatchers.IO) {
            for (i in 0..14) {
                delay(1000)
                Log.d(TAG, "counter 1 called $i")
            }
        }
    }

    private suspend fun timerCounter2() {
        withContext(Dispatchers.IO) {
            for (i in 0..19) {
                delay(1000)
                Log.d(TAG, "counter 2 called $i")
            }
        }
    }

    private suspend fun timeCounter3() {
        withContext(Dispatchers.IO) {
            for (i in 0..24) {
                delay(1000)
                Log.d(TAG, "counter 3 called $i")
            }
        }
    }

    private suspend fun timeCounter4() {
        withContext(Dispatchers.IO) {
            for (i in 0..11) {
                delay(1000)
                Log.d(TAG, "counter 4 called $i")
            }
        }
    }
}