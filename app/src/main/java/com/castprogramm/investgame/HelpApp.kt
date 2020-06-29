package com.castprogramm.investgame

import android.app.Application
import android.content.Context
import android.provider.Settings
import java.security.AccessControlContext

class HelpApp: Application(){
    override fun onCreate() {
        super.onCreate()
        globalContext = applicationContext
    }
    companion object{
        lateinit var globalContext: Context
    }
}