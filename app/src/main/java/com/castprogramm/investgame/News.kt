package com.castprogramm.investgame

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class News: Up {
    var name: String? = null
    var eventType: String? = null
    var msg: String = ""
    var msgs1: Array<String> = arrayOf(
        "Хорошие новости! В $name произошло $eventType. Акционерам" +
                " салам, остальным соболезнуем",
        "Брокеры в шоке! Зафиксирован небывалый $eventType цен в $name ",
        "Сегодня случилось хорошее событие: акции в $name поддались $eventType"
    )
    var msgs2: Array<String> = arrayOf(
        "Каждый акционер отправляется на помойку. Небывалый $eventType в $name заставил" +
                "владельцев задуматься о поиске новой работы.",
        "История кэшберри: все владельцы акций в $name прочувствовали $eventType на себе",
        "Этот день вы точно запомните! Алексей упал в обморок, когда открыл биржу..."
    )
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
                eventType = "кризис"
                TypeEvent.CRYSIS
            }
            in 5..15 ->  {sadmessage()
                eventType = "обвал"
                TypeEvent.OBVAL
            }
            in 15..85 ->  TypeEvent.NOTHING
            in 85..90 ->  {funnymessage()
                eventType = "подъем"
                TypeEvent.PODEM
            }
            in 90..100 ->  {funnymessage()
                eventType = "рост"
                TypeEvent.INCREASE
            }
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
        msg = msgs1[(0..msgs1.size-1).random()]
        Log.d("debug", msg)
    }

    override fun funnymessage() {
        msg = msgs2[(0..msgs2.size-1).random()]
        Log.d("debug", msg)
    }

    init {
        allcountries = mutableListOf("Россия", "Пендосия", "Украина")
        events = mutableListOf("повышение", "понижение")
        eventType = events[(0..events.size-1).random()]
        name = allcountries[(0..allcountries.size-1).random()]
        msg = "В стране $name произошло $eventType цен"
    }

}
class Industry: News(){
    override fun sadmessage() {
        msg = msgs1[(0..msgs1.size-1).random()]
        Log.d("debug", msg)
    }

    override fun funnymessage() {
        msg = msgs2[(0..msgs2.size-1).random()]
        Log.d("debug", msg)
    }

    init {
        events = mutableListOf("прорыв", "упадок")
        allcountries = mutableListOf("Россия", "Пендосия", "Украина")
        eventType = events[(0..events.size-1).random()]
        name = allcountries[(0..(allcountries.size-1)).random()]
        msg = "В отрасли $name произошел $eventType"
    }
}
class Enterprise: News(){
    override fun sadmessage() {
        msg = msgs1[(0..msgs1.size-1).random()]
        Log.d("debug", msg)
    }

    override fun funnymessage() {
        msg = msgs2[(0..msgs2.size-1).random()]
        Log.d("debug", msg)
    }

    init {
        events = mutableListOf("кризис", "прикол")
        allcountries = mutableListOf("GDFdfdgf", "gfd", "fdgfgdf", "dfgfd")
        name = allcountries[(0..allcountries.size-1).random()]
        eventType = events[(0..events.size-1).random()]
        msg = "В компании $name произошел $eventType"
    }
}
class NewsAdapter(): RecyclerView.Adapter<NewsAdapter.Companion.NewsViewHolder>(){
    var msgs : MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.recycle_msg, parent, false)
        return NewsViewHolder(
            eee
        )
    }

    override fun getItemCount(): Int = msgs.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(msgs[position])

    companion object{
        class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_msg)
            fun bind(string: String){
                name.setText(string)
            }
        }
    }
}