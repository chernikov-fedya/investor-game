package com.castprogramm.investgame.broker

import androidx.lifecycle.MutableLiveData
import com.castprogramm.investgame.Expense
import com.castprogramm.investgame.Up
import com.castprogramm.investgame.stock.Stock
import kotlin.math.round

object Broker: Up {
    var name = "Нажмите, чтобы ввести"
    var myStock = mutableListOf<Stock>()  //массив акций, которыми владеет брокер
    var wallet: Double = 10000.0  // сколько есть денег у брокера
    var myStockCost: Double = 0.0 // стоимость акций брокера
    var thisWallet : MutableLiveData<Double> = MutableLiveData()
    var thisLess : MutableLiveData<Double> = MutableLiveData()
    var thisMyStock : MutableLiveData<Double> = MutableLiveData()
    var less : Double = 0.0

    // округление до сотых
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
        for (i in 0..myStock.size-1){  // считаем стоимость акций у брокера
            myStockCost += (myStock[i].cost * myStock[i].quantity)
        }
        thisMyStock.value = myStockCost
        thisWallet.value = wallet  // передаем значения в LiveData для отрисовки графика
        thisLess.value = less
    }
}