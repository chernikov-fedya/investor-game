package com.castprogramm.investgame.tools

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.castprogramm.investgame.database.DBOpenSQLite
import com.castprogramm.investgame.HelpApp
import com.castprogramm.investgame.R
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.stock.Stoks
import java.lang.Exception

@Suppress("DEPRECATION")
class SaveService: IntentService("Save"){
    var TAG = "SIZE"
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Сохранение началось")
    }

    override fun onHandleIntent(p0: Intent?) {
        try {
            val notification = NotificationCompat.Builder(this, HelpApp().chanel_id)
                .setContentTitle("Идёт сохранение")
                .setContentText("Пожалуйста, не выкидывайте из памяти приложение")
                .setSmallIcon(R.drawable.chart)
                .build()
            startForeground(1, notification)
            Log.d(TAG, "Save")
            val dbhelper = DBOpenSQLite(this, null)
            dbhelper.addStock(Broker.myStock)
            dbhelper.addDataPoint(Stoks.allStoks)
//                Log.d("SIZE", "Вывод: количество цен у акции" + it.costs.size.toString())
            dbhelper.close()
        }
        catch(e:Exception){
            Log.d(TAG, e.message!!)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "IntentService is dead :(")
    }
}