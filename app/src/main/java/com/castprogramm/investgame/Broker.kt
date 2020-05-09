package com.castprogramm.investgame

object Broker {
    var name = "Alexsey"
    var myStock = mutableListOf(Stoks.allStoks[0])
    var wallet: Double = 0.0 // сколько есть денег у брокера
    fun fill(){ //закупаем акции
        for (i in 0..(2..5).random()){
           // myStock?.add(Stock("крутыши", (0..3).random().toDouble()))
        }
    }
}