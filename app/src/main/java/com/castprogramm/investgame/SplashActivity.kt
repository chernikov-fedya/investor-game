package com.castprogramm.investgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.RoomDatabase
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.database.DBOpenSQLite
import com.castprogramm.investgame.database.StockDataBase
import com.castprogramm.investgame.stock.Stoks
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.concurrent.timerTask


class SplashActivity : AppCompatActivity() {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)
        scope.launch {
            delay(1500)
        }.invokeOnCompletion {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
