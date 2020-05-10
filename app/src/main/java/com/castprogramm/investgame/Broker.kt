package com.castprogramm.investgame

object Broker: Up {
    var name = "Alexsey"
    var myStock = mutableListOf<Stock>()
    var wallet: Double = 10000.0// сколько есть денег у брокера
    var myStockCost: Double = 0.0

    override fun update() {
        myStockCost = 0.0
        myStockCost = wallet
        for (i in 0..myStock.size-1){
            myStockCost = myStockCost + myStock[i].cost * myStock[i].quantity
        }
    }
}