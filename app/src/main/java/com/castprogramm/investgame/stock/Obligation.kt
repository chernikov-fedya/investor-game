package com.castprogramm.investgame.stock

import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.database.DBDataPoint

class Obligation: Stock() {

    var timeAfterBuy = 0
    override fun update() { // Переопределение функции из интерфейса Up
        costs.add(DBDataPoint(costs.size.toDouble(), cost, this.name)) // Добавление в список новой серии значений
        costsofStock.value = costs // Присвоение в хранилище списка с сериями данных
        timeAfterBuy ++
    }
    fun checkSold(): Boolean = timeAfterBuy >= 100

}