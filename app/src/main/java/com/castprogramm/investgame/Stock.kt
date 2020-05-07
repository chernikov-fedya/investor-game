package com.castprogramm.investgame

import android.util.ArrayMap
import java.util.*

class Stock(
    var name: String,
    var cost: Double
): Up {

    var costsofStock : MutableMap<String, String> = mutableMapOf()
    override fun update() {
        costsofStock.put(Date().toString(), (0..1000).random().toString())
    }
}