package com.castprogramm.investgame

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.room.Room
import com.castprogramm.investgame.database.SaveMyStocksDataBase
import com.castprogramm.investgame.database.StockDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.security.AccessControlContext

class HelpApp: Application(){
    val module = module {
        single { Room.databaseBuilder(this@HelpApp.applicationContext, StockDataBase::class.java, "database.db").build()}
        single { Room.databaseBuilder(this@HelpApp.applicationContext, SaveMyStocksDataBase::class.java, "my_stocks.db").build()}
    }

    var chanel_id = "Save"
    override fun onCreate() {
        super.onCreate()
        globalContext = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@HelpApp)
            modules(module)
        }
    }

    companion object{
        lateinit var globalContext: Context
    }

}