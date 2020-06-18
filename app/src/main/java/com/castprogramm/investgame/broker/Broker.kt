package com.castprogramm.investgame.broker

import androidx.lifecycle.MutableLiveData
import com.castprogramm.investgame.LossEvent
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
    var thisEnd : MutableLiveData<Double> = MutableLiveData()
    var loss : Double = 0.0
    var allMoney: Double = 1.0
    // округление до сотых
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
    override fun update() {
        myStockCost = 0.0
        for (i in 0..myStock.size-1){  // считаем стоимость акций у брокера
            myStockCost += (myStock[i].cost * myStock[i].quantity)
        }
        // получаем случайное событие типа расход и задаем расход
            when ((0..100).random()) {
                in 1..15 ->  {
                    if ((wallet + myStockCost) / 1000 <= 10)
                        loss = 10.0
                    else
                        loss = (wallet + myStockCost) / 1000
                    loss = loss.toInt().toDouble()
                    LossEvent.Illness
                }
                in 15..70 ->  {
                    if ((wallet + myStockCost) / 1000 <= 10)
                        loss = 10.0
                    else
                        loss = (wallet + myStockCost) / 1000
                    loss = loss.toInt().toDouble()
                    LossEvent.Food
                }
                in 70..71 ->  LossEvent.Nothing
                in 72..90 ->  {
                    if ((wallet + myStockCost) / 1000 <= 10)
                        loss = 10.0
                    else
                        loss = (wallet + myStockCost) / 1000
                    loss = loss.toInt().toDouble()
                    LossEvent.Transport
                }
                in 90..100 ->  {
                    if ((wallet + myStockCost) / 1000 <= 10)
                        loss = 10.0
                    else
                        loss = (wallet + myStockCost) / 1000
                    loss = loss.toInt().toDouble()
                    LossEvent.Family
                }
                else -> LossEvent.Nothing
            }
        allMoney = wallet + myStockCost
        wallet = wallet - loss
        thisEnd.value = allMoney
        thisMyStock.value = myStockCost
        thisWallet.value = wallet  // передаем значения в LiveData для обновления значений
        thisLess.value = loss
    }
}