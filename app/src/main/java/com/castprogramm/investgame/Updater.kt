package com.castprogramm.investgame

import android.os.Handler

// класс для обновления состояния всех объектов
class Updater(var handler: Handler) : Runnable{
    companion object{
        val UPDATE_TIME : Long = 5000 // период обновления
    }
    var objectsToUpdate : MutableList<Up> = mutableListOf()
    var timePoint : Long // нужна для расчета врмени которое прошло с предыдущего обновления
    var runFlag = true
    init {
        timePoint = System.currentTimeMillis()
    }
    override fun run() {
            var nowTime = System.currentTimeMillis()
            if (nowTime - timePoint > UPDATE_TIME) {
                objectsToUpdate.forEach { it.update() }
                timePoint = nowTime
            }
        if (runFlag)
            handler.postDelayed(this, UPDATE_TIME)
    }
}

// данный интерфейс реализуют классы, которые хотят получать обновления
interface Up {
    fun update()
}