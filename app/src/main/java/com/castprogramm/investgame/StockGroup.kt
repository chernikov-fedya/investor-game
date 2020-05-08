package com.castprogramm.investgame

class StockGroup {
    var name : String? = null
    var grouplist: MutableList<Stock> = mutableListOf()
    fun changePrice(typeEvent: TypeEvent){
        var a: Double = when(typeEvent){
            TypeEvent.CRYSIS -> 0.8
            TypeEvent.OBVAL -> 0.87
            TypeEvent.NOTHING -> 1.0
            TypeEvent.PODEM -> 1.05
            TypeEvent.INCREASE ->1.14
            else -> 1.0
        }

        for (i in 0..grouplist.size-1){
        grouplist[i].cost = grouplist[i].cost?.times(a)

    }
    }
}