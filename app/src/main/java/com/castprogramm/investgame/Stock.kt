package com.castprogramm.investgame

import android.util.ArrayMap
import java.util.*

class Stock: Up {
    var name: String? = null
    var cost: Double? = null
    var costsofStock : MutableMap<String, String> = mutableMapOf()
    override fun update() {
        costsofStock.put(Date().toString(), (0..1000).random().toString())
    }
}