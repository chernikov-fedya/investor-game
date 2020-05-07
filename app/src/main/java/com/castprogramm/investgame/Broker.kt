package com.castprogramm.investgame

class Broker {
    var wallet: Double? = null // сколько есть денег у брокера
    var myStock: MutableList<Stock>? = null
    fun fill(){ //закупаем акции
        for (i in 0..(2..5).random()){
            myStock?.add(Stock("крутыши", (0..3).random().toDouble()))
        }
    }
}