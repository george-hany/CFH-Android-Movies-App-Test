package com.core.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import org.json.JSONObject
import java.io.InputStream

object CommonUtils {
    fun loadJSONFromAsset(
        context: Context,
        jsonFileName: String,
    ): String {
        val manager = context.assets
        val inputStream: InputStream = manager.open(jsonFileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    }

    fun Activity.getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getMessage(string: String?): String? {
        return try {
            val message = JSONObject(string)
            message.getString("message")
        } catch (e: Exception) {
            "Error"
        }
    }
}
