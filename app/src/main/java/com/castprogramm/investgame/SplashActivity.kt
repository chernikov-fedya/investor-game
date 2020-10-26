package com.castprogramm.investgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.stock.Stoks
import kotlinx.coroutines.*


class SplashActivity : AppCompatActivity() {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)
        val job = scope.launch {
            val result = async {
                val dbhadler = DBOpenSQLite(this@SplashActivity, null)
                val cursor = dbhadler.getAllStock()
                if (cursor!=null && cursor.count > 0){
                    cursor.moveToFirst()
                    Broker.myStock.put(
                        Stoks.allStoks.find { it.companies?.name ==  cursor.getString(cursor.getColumnIndex(DBOpenSQLite.COLOUM_COMPANY_NAME))}.apply {
                            this!!.cost = cursor.getDouble(cursor.getColumnIndex(DBOpenSQLite.COLOUM_CENT)) }!!,
                        cursor.getInt(cursor.getColumnIndex(DBOpenSQLite.COLOUM_QUANTITY)))
                    while (cursor.moveToNext()){
                        Broker.myStock.put(
                            Stoks.allStoks.find { it.companies?.name ==  cursor.getString(cursor.getColumnIndex(DBOpenSQLite.COLOUM_COMPANY_NAME))}.apply {
                                this!!.cost = cursor.getDouble(cursor.getColumnIndex(DBOpenSQLite.COLOUM_CENT)) }!!,
                            cursor.getInt(cursor.getColumnIndex(DBOpenSQLite.COLOUM_QUANTITY)))}
                    cursor.close()}
                Stoks.allStoks.forEach {
                    dbhadler.readDataPoint(it)
                    Log.d("SIZE", it.companies?.name + " ${it.costs.size}")
                }
                Stoks.allMax = dbhadler.getMaxSize()
                dbhadler.close()

            }.await()
        }
        Thread.sleep(1000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
