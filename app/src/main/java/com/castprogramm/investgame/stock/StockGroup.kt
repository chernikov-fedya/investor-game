package com.castprogramm.investgame.stock

import com.castprogramm.investgame.news.TypeEvent

class StockGroup {
    var name : String? = null
    var grouplist: MutableList<Stock> = mutableListOf()
    fun changePrice(typeEvent: TypeEvent){
        var a: Double = when(typeEvent){
            TypeEvent.CRYSIS -> 0.8
            TypeEvent.OBVAL -> 0.9
            TypeEvent.NOTHING -> 1.0
            TypeEvent.PODEM -> 1.1
            TypeEvent.INCREASE ->1.20
            else -> 1.0
        }
        for (i in 0..grouplist.size-1){
        grouplist[i].cost = grouplist[i].cost.times(a)
    }
    }
}