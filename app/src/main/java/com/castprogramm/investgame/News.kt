package com.castprogramm.investgame

import android.util.Log

abstract class News: Up {
    var name: String? = null
    var eventType: String? = null
    var msg: String = ""
    override fun update() {
        for (i in 0..arrayStockGroup.size-1){
            arrayStockGroup[i].changePrice(makeEvent())
        }
    }
    open fun sadmessage(){

    }
    open fun funnymessage(){

    }
    fun makeEvent():TypeEvent =
         when ((0..100).random()) {
            in 1..5 ->  {sadmessage()
                TypeEvent.CRYSIS
                }
            in 5..15 ->  {sadmessage()
                TypeEvent.OBVAL}
            in 15..85 ->  TypeEvent.NOTHING
            in 85..90 ->  {funnymessage()
                TypeEvent.PODEM}
            in 90..100 ->  {funnymessage()
                TypeEvent.INCREASE}
             else -> TypeEvent.NOTHING
        }

    var events: MutableList<String> = mutableListOf()
    var allcountries: MutableList<String> = mutableListOf()
    var arrayStockGroup: MutableList<StockGroup> = mutableListOf()
    init {

    }

/* lateinit var country: String
 var countryM: Array<String> = arrayOf("Россия", "Пендосия", "Украина")
 lateinit var eventType: String
 var eventTypeM: Array<String> = arrayOf("повышение", "понижение")
 var msg: ring = ""
     init {
     country = countryM[(0..countryM.size-1).random()]
     eventType = eventTypeM[(0..eventTypeM.size-1).random()]
     msg = "В стране $country произошло $eventType цен"
 }*/

}
enum class TypeEvent(){
    CRYSIS,
    OBVAL,
    NOTHING,
    PODEM,
    INCREASE
}
class Country(): News() {

    override fun sadmessage() {
        msg = "Все очень грустно "
        Log.d("debug", msg)
    }

    override fun funnymessage() {
     msg = "Произошло позитивное событие"
        Log.d("debug", msg)
    }

    init {
         msg = "В стране произошло $eventType цен"
         events = mutableListOf("повышение", "понижение")
        eventType = events[(0..events.size-1).random()]
         allcountries = mutableListOf("Россия", "Пендосия", "Украина")
         name = allcountries[(0..allcountries.size-1).random()]
     }

}
class Industry: News(){


    init {
        events = mutableListOf("прорыв", "упадок")
        eventType = events[(0..events.size-1).random()]
        msg = "В отрасли $name произошел $eventType"
        allcountries = mutableListOf("")
        name = allcountries[(0..(allcountries.size-1)).random()]
    }
}
class Enterprise: News(){


    init {
        events = mutableListOf("кризис", "прикол")
        eventType = events[(0..events.size-1).random()]
        msg = "В компании $name произошел $eventType"
        events = mutableListOf("кризис", "прикол")
        allcountries = mutableListOf("GDFdfdgf", "gfd", "fdgfgdf", "dfgfd")
        name = allcountries[(0..allcountries.size-1).random()]
    }
}