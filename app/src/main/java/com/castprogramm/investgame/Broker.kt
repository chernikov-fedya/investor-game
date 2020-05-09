package com.castprogramm.investgame

object Broker {
    var name = "Alexsey"
    var myStock = mutableListOf<Stock>()
    var wallet: Double = 10000.0 // сколько есть денег у брокера
    fun fill(){ //закупаем акции
        for (i in 0..(2..5).random()){
           // myStock?.add(Stock("крутыши", (0..3).random().toDouble()))
        }
    }
}