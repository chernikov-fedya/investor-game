package com.castprogramm.investgame

import android.os.Build
import android.provider.ContactsContract
import android.util.ArrayMap
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.jjoe64.graphview.series.DataPoint
import java.time.format.DateTimeFormatter
import java.util.*

open class Stock: Up {
    var name: String? = null
    var cost: Double? = null
    var costsofStock : MutableLiveData<MutableList<DataPoint>> = MutableLiveData()
    var costs : MutableList<DataPoint> = mutableListOf()
//class Stock(
//    var name: String,
//    var cost: Double
//): Up {
//
//    var costsofStock : MutableMap<String, String> = mutableMapOf()
    override fun update() {
        costs.add(DataPoint(Date() , (100..1000).random().toDouble()))
        costsofStock.value = costs
    }
}

class StockPoint(cost : Double): Stock() {
    var costss: Double = cost
    var date = Date()
}