package com.castprogramm.investgame.broker

import androidx.lifecycle.MutableLiveData
import com.castprogramm.investgame.Up
import com.castprogramm.investgame.stock.Stock
import kotlin.math.round

object Broker: Up {
    var name = "Нажмите, чтобы ввести"
    var myStock = mutableListOf<Stock>()
    var wallet: Double = 10000.0// сколько есть денег у брокера
    var myStockCost: Double = 0.0
    var thisWallet : MutableLiveData<Double> = MutableLiveData()
    var thisLess : MutableLiveData<Double> = MutableLiveData()
    var less : Double = 0.0

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
    override fun update() {
        myStockCost = 0.0
        var expense = Expense()
        less = expense.loss
        wallet = wallet - expense.loss
        for (i in 0..myStock.size-1){
            myStockCost = (myStockCost + myStock[i].cost * myStock[i].quantity).round(2)
        }
        thisWallet.value =
            wallet
        thisLess.value =
            less
    }
}