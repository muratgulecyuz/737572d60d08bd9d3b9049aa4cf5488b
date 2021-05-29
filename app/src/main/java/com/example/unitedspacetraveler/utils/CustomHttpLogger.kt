package com.example.unitedspacetraveler.utils

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "CustomHttpLogger"
        if (message.startsWith("{") || message.startsWith("[")) {
            LogUtils.json("CustomHttpLogger", message)
        } else {
            Log.d(logName, message)
            return
        }
    }

}