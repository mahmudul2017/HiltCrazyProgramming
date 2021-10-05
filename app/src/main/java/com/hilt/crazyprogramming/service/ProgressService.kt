package com.hilt.crazyprogramming.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class ProgressService : IntentService("ProgressService") {
    private val t: Timer = Timer()

    companion object {
        var MY_ACTION = "com.hilt.crazyprogramming.service"
    }

    override fun onHandleIntent(intent: Intent?) {
        // intent!!.getStringExtra()
        for (i in 1..100) {
            var progressValue = i
            Thread.sleep(1000)
            val customIntent = Intent(MY_ACTION)
            val value = customIntent!!.putExtra("progressValue", progressValue)
            sendBroadcast(value)
            Log.d("ProgressService", "service called")

            /* t.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm")
                    val currentTime: String = sdf.format(c.time)

                    var progressValue = i
                    *//* for (i in 1..100) {
                        progressValue = i
                    } *//*

                    progressValue++

                    val customIntent = Intent(MY_ACTION)
                    val value = customIntent!!.putExtra("progressValue", progressValue)
                    sendBroadcast(value)
                    Log.d("ProgressService", "service called")
                }
            }, 0, 1000) */
        }
    }
}