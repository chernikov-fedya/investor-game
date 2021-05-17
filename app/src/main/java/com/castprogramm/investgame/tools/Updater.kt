package com.castprogramm.investgame.tools

import android.os.Handler

// класс для обновления состояния всех объектов
class Updater(var handler: Handler, var UPDATE_TIME : Long = 5000) : Runnable{
    var play: Boolean = true
    var objectsToUpdate : MutableList<Up> = mutableListOf()
    var timePoint : Long // нужна для расчета врмени которое прошло с предыдущего обновления
    var runFlag = true // проверка на состояние MainActivity
    init {
        timePoint = System.currentTimeMillis()
    }
    override fun run() {
        if (play){
            var nowTime = System.currentTimeMillis()
            if (nowTime - timePoint > UPDATE_TIME) {
                objectsToUpdate.forEach { it.update() } // овызывает у всего массива метод update
                timePoint = nowTime
            }
        if (runFlag)
            handler.postDelayed(this, UPDATE_TIME) // Зацикливание функции
        }
    }
}

// данный интерфейс реализуют классы, которые хотят получать обновления
interface Up {
    fun update()
}