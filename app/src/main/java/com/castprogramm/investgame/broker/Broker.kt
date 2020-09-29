package com.castprogramm.investgame.broker

import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.castprogramm.investgame.LossEvent
import com.castprogramm.investgame.Up
import com.castprogramm.investgame.stock.Obligation
import com.castprogramm.investgame.stock.Stock
import kotlin.math.round

object Broker: Up {
    var name = "Нажмите, чтобы ввести"
    var myStock = mutableMapOf<Stock, Int>()  //массив акций, которыми владеет брокер
    var myObligation = mutableMapOf<Obligation, Int>() //массив облигаций, которыми владеет брокер
    var wallet: Double = 30.0  // сколько есть денег у брокера
    var myStockCost: Double = 0.0 // стоимость акций и облигаций брокера
    var lossObligation = mutableListOf<Obligation>()
    var thisWallet : MutableLiveData<Double> = MutableLiveData()
    var thisLess : MutableLiveData<Double> = MutableLiveData()
    var thisMyStock : MutableLiveData<Double> = MutableLiveData()
    var thisEnd : MutableLiveData<Double> = MutableLiveData()
    var loss : Double = 0.0
    var allMoney: Double = 1.0

    override fun update() {
        for (i in myObligation){ // проверка на окончание времени нахождения у пользователя и возврат денег брокеру при окончании его
            if (i.key.checkSold()){
                wallet += (i.key.cost * i.value)
                lossObligation.add(i.key)
            }
        }
        for (i in lossObligation){ // удаление облигаций, у который истёк срок
            myObligation.remove(i)
        }
        lossObligation.clear()
        myStockCost = 0.0
        for (i in myStock){  // считаем стоимость акций у брокера
            myStockCost += (i.key.cost * i.value)
        }

        for (i in myObligation){ // считаем стоимость всех облигаций у брокера
            myStockCost += (i.key.cost * i.value)
        }

        loss = getLossMoney()
        allMoney = wallet + myStockCost
        wallet -= loss
        thisEnd.value = allMoney
        thisMyStock.value = myStockCost
        thisWallet.value = wallet  // передаем значения в LiveData для обновления значений
        thisLess.value = loss
    }
    fun getLossMoney(): Double{ // получаем случайное событие типа расход и задаем расход
        var minusMoney = 0.0
        when ((0..100).random()) {
            in 1..15 ->  {
                if ((wallet + myStockCost) / 1000 <= 10)
                    loss = 10.0
                else
                    minusMoney = (wallet + myStockCost) / 1000
                minusMoney = minusMoney.toInt().toDouble()
                LossEvent.Illness
            }
            in 15..70 ->  {
                if ((wallet + myStockCost) / 1000 <= 10)
                    loss = 10.0
                else
                    minusMoney = (wallet + myStockCost) / 1000
                minusMoney = minusMoney.toInt().toDouble()
                LossEvent.Food
            }
//            in 70..71 ->  LossEvent.Nothing
            in 71..90 ->  {
                if ((wallet + myStockCost) / 1000 <= 10)
                    minusMoney = 10.0
                else
                    minusMoney = (wallet + myStockCost) / 1000
                minusMoney = minusMoney.toInt().toDouble()
                LossEvent.Transport
            }
            in 90..100 ->  {
                if ((wallet + myStockCost) / 1000 <= 10)
                    minusMoney = 10.0
                else
                    minusMoney = (wallet + myStockCost) / 1000
                minusMoney = minusMoney.toInt().toDouble()
                LossEvent.Family
            }
            else -> LossEvent.Nothing
        }
        return minusMoney
    }
}