package com.hilt.crazyprogramming.roomDB.utils

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log

object BitmapScalingHelper {
    fun decodeResource(res: Resources, resId: Int, dstWidth: Int, dstHeight: Int): Bitmap? {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inJustDecodeBounds = false
        options.inSampleSize = calculateSampleSize(
            options.outWidth, options.outHeight, dstWidth,
            dstHeight
        )
        options = BitmapFactory.Options()
        //May use null here as well. The function may interpret the pre-used options variable in ways hard to tell
        val unscaledBitmap = BitmapFactory.decodeResource(res, resId, options)
        if (unscaledBitmap == null) {
            Log.e("ERR", "Failed to decode resource - $resId $res")
            return null
        }
        return unscaledBitmap
    }

    private fun calculateSampleSize(outWidth: Int, outHeight: Int, dstWidth: Int, dstHeight: Int): Int {
         return 5
    }
}