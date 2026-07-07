package com.xiaomi.subscreencenter.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class SubScreenService : Service() {

    companion object {
        private const val TAG = "SubScreenService"
        var mainHandler: Handler? = null
    }

    private val subScreenBinder = object : ISubScreen.Stub() {
        // TO-DO: figure what ISubScreen contains
    }

    override fun onCreate() {
        super.onCreate()
        mainHandler = Handler(Looper.getMainLooper())
        
        Log.d(TAG, "Initializing SubScreen Service.")
        
        // Xiaomi call SmartAssistant after subscreen initialized
        Log.d(TAG, "SubScreen hardware bridge mock active.")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "System bound to SubScreen interface.")
        return subScreenBinder.asBinder()
    }

    override fun onDestroy() {
        mainHandler?.removeCallbacksAndMessages(null)
        mainHandler = null
        Log.d(TAG, "SubScreen Service destroyed.")
        super.onDestroy()
    }
}