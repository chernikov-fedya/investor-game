package com.castprogramm.investgame

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.EnumClasses.Countries
import com.castprogramm.investgame.EnumClasses.Industries
import com.castprogramm.investgame.Stoks.newsarray

abstract class News: Up {
    companion object{
        // функция для фасовки всех акций и прикрепления к апдейтеру
        fun fillNews(updater: Updater){
            // создание групп стран
            var countries : Array<StockGroup> = Array(Countries.values().size)
                {StockGroup().apply { name =  Countries.values()[it].name }}

            // создание групп отраслей
            var industries : Array<StockGroup> = Array(Industries.values().size)
            {StockGroup().apply { name =  Industries.values()[it].name }}

            // создание группы на кажду акцию
            var companies = Array<StockGroup>(Stoks.allStoks.size) {
                StockGroup().apply{
                    name = Stoks.allStoks[it].name
                    grouplist.add(Stoks.allStoks[it])
                }
            }
                // распределение всех акций по группам выше
            for (i in Stoks.allStoks){
                countries.find {i.companies?.country?.name == it.name}?.grouplist?.add(i)
                industries.find {i.companies?.industry?.name == it.name}?.grouplist?.add(i)
            }
                // созданием объектов - новоснтных лент для каждого типа
            var newMakers = arrayOf(
                Country().apply { arrayStockGroup = countries.toMutableList()},
                Industry().apply { arrayStockGroup = industries.toMutableList()},
                Enterprise().apply { arrayStockGroup = companies.toMutableList()}
            )
            // прикрепление новостных лент к апдейтеру
            updater.objectsToUpdate.addAll(newMakers)
        }
    }
    var name: String? = null
    var eventType: String? = null
    var msg: String = ""

    override fun update() {
        for (i in 0..arrayStockGroup.size-1){
            arrayStockGroup[i].changePrice(makeEvent())
        }
    }
    abstract fun sadmessage()
    abstract fun funnymessage()

    fun makeEvent():TypeEvent =
        when ((0..1000).random()) {
            in 1..5 ->  {eventType = "кризис"
                sadmessage()
                TypeEvent.CRYSIS
            }
            in 5..15 ->  { eventType = "обвал"
                sadmessage()
                TypeEvent.OBVAL
            }
            in 985..995 ->  {eventType = "подъем"
                funnymessage()
                TypeEvent.PODEM
            }
            in 995..1000 ->  {eventType = "рост"
                funnymessage()
                TypeEvent.INCREASE
            }
            else -> TypeEvent.NOTHING
        }

    var events: MutableList<String> = mutableListOf()
    var arrayStockGroup: MutableList<StockGroup> = mutableListOf()
}
enum class TypeEvent(){
    CRYSIS,
    OBVAL,
    NOTHING,
    PODEM,
    INCREASE
}
class Country: News() {
    init {
        events = mutableListOf("повышение", "понижение")
        eventType = TypeEvent.values()[(0..TypeEvent.values().size-1).random()].name
        name = Countries.values()[(0..Countries.values().size-1).random()].name
        msg = "В стране $name произошло $eventType цен"
    }

    override fun sadmessage(){
        Log.d("debug", msg)
        events = mutableListOf("повышение", "понижение")
        eventType = "понижение"
        name = Countries.values()[(0..Countries.values().size-1).random()].s
        msg = "В стране $name произошло $eventType цен"
        newsarray.add(0, msg)
    }

    override fun funnymessage() {
        Log.d("debug", msg)
        events = mutableListOf("повышение", "понижение")
        eventType = "повышение"
        name = Countries.values()[(0..Countries.values().size-1).random()].s
        msg = "В стране $name произошло $eventType цен"
        newsarray.add(0, msg)
    }
}
class Industry: News(){
    init {
    events = mutableListOf("прорыв", "упадок")
    eventType = events[(0..events.size-1).random()]
    name = Industries.values()[(0..Industries.values().size-1).random()].n
    msg = "В отрасли $name произошел $eventType"

}
    override fun sadmessage() {
        Log.d("debug", msg)
        events = mutableListOf("прорыв", "упадок")
        eventType = events[1]
        name = Industries.values()[(0..Industries.values().size-1).random()].n
        msg = "В отрасли $name произошел $eventType"
        newsarray.add(0, msg)
    }

    override fun funnymessage() {
        Log.d("debug", msg)
        events = mutableListOf("прорыв", "упадок")
        eventType = events[0]
        name = Industries.values()[(0..Industries.values().size-1).random()].n
        msg = "В отрасли $name произошел $eventType"
        newsarray.add(0, msg)
    }


}
class Enterprise: News(){
    init {
    events = mutableListOf("кризис", "прикол")
    name = Companies.values()[(0..Companies.values().size-1).random()].n
    eventType = events[(0..events.size-1).random()]
    msg = "В компании $name произошел $eventType"

}
    override fun sadmessage() {
        Log.d("debug", msg)
        events = mutableListOf("кризис", "прикол")
        name = Companies.values()[(0..Companies.values().size-1).random()].n
        eventType = events[0]
        msg = "В компании $name произошел $eventType"
        newsarray.add(0, msg)
    }

    override fun funnymessage() {
        Log.d("debug", msg)
        events = mutableListOf("кризис", "прикол")
        name = Companies.values()[(0..Companies.values().size-1).random()].n
        eventType = events[1]
        msg = "В компании $name произошел $eventType"
        newsarray.add(0, msg)
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