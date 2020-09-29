package com.castprogramm.investgame.stock

import com.jjoe64.graphview.series.DataPoint

class Obligation: Stock() {
    var timeAfterBuy = 0
    override fun update() { // Переопределение функции из интерфейса Up
        costs.add(DataPoint(costs.size.toDouble(), cost)) // Добавление в список новой серии значений
        costsofStock.value = costs // Присвоение в хранилище списка с сериями данных
    }
    fun checkSold(): Boolean = timeAfterBuy >= 100

}