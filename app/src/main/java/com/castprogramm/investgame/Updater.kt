package com.castprogramm.investgame

// класс для обновления состояния всех объектов
class Updater : Thread(){
    companion object{
        val UPDATE_TIME = 5000 // период обновления
    }
    var objectsToUpdate : MutableList<Up> = mutableListOf()
    var timePoint : Long // нужна для расчета врмени которое прошло с предыдущего обновления
    init {
        timePoint = System.currentTimeMillis()
    }
    override fun run() {
        super.run()
        var nowTime = System.currentTimeMillis()
        if ( timePoint - nowTime > UPDATE_TIME){
            objectsToUpdate.forEach { it.update() }
        }
    }
}

// данный интерфейс реализуют классы, которые хотят получать обновления
interface Up {
    fun update()
}