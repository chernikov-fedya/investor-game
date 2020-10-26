package com.castprogramm.investgame

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.AccessControlContext

class HelpApp: Application(){
    var chanel_id = "Save"
    override fun onCreate() {
        super.onCreate()
        globalContext = applicationContext
    }

    companion object{
        lateinit var globalContext: Context
    }
    fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var serviceChanel = NotificationChannel(
                chanel_id,
                "Test",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            var manager = getSystemService(NotificationManager :: class.java)
            manager.createNotificationChannel(serviceChanel)
        }
    }
}