package com.castprogramm.investgame.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint

import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.Up

// Класс для создания акции
class Stock: Up {
    var name: String = "" // Название акции
    var cost: Double = 0.0 // Стоимость акции
    var quantity: Int = 0  // Количество акций этого типа
    var costsofStock : MutableLiveData<MutableList<DataPoint>> = MutableLiveData() // LiveData, которая хранит в себе изменяемый список DataPoint
    var costs : MutableList<DataPoint> = mutableListOf() // Изменяемый список, который присваивается в LiveData
    var companies: Companies? = null // Компания, к которой принадлежит акция
    override fun update() { // Переопределение функции из интерфейса Up
        costs.add(DataPoint(costs.size.toDouble(), cost)) // Добавление в список новой серии значений
        costsofStock.value = costs // Присвоение в хранилище списка с сериями данных
    }
}
// Адаптер для вывода списка акиций в фрагмент активов с помощью RecyclerView
class StockAdapter(): RecyclerView.Adapter<StockAdapter.Companion.StockViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false) // Переменная для хранения файла разметки
        return StockViewHolder(eee)
    }

    override fun getItemId(position: Int): Long = position.toLong() //

    override fun getItemCount(): Int = Stoks.allStoks.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(Stoks.allStoks[position])
    }

    override fun onViewDetachedFromWindow(holder: StockViewHolder) {
        holder.newtest1?.removeObservers(fragment!!)
    }

    companion object{
        var fragmentManager : FragmentManager? = null
        var fragment: AllStockFragment? = null
        class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_st)
            var cost: TextView = itemView.findViewById(R.id.cost_st)
            var cardView : CardView = itemView.findViewById(R.id.cardinal)
            var image: ImageView = itemView.findViewById(R.id.icon_comp)
            var newtest1 : MutableLiveData<MutableList<DataPoint>>? = null
            fun bind(stock: Stock){
                name.setText(stock.companies?.n)
                cost.setText(stock.cost.toString())
                image.setImageResource(stock.companies?.r!!)
                newtest1 = stock.costsofStock
                newtest1?.observe(fragment!!, androidx.lifecycle.Observer {
                    cost.setText("%.2f".format(it.last().y))
                })
                cardView.setOnClickListener {
                    val fm =
                        fragmentManager
                    val ft = fm?.beginTransaction()
                    var f =
                        StockFragment.instfragment(
                            stock
                        )
                    ft?.replace(R.id.frame_menu, f)
                    ft?.addToBackStack(null)
                    ft?.commit()
                }
            }
        }
    }
}
class BrokerAdapter(): RecyclerView.Adapter<BrokerAdapter.Companion.BrokerViewHolder>(){
    var stocks : MutableList<Stock> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrokerViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.broker_recycle, parent, false)
        return BrokerViewHolder(
            eee
        )
    }

    override fun getItemCount(): Int = stocks.size

    override fun onBindViewHolder(holder: BrokerViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    companion object{
        var fragmentManager : FragmentManager? = null
        class BrokerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_stb)
            var cost: TextView = itemView.findViewById(R.id.cost_stb)
            var quantity: TextView = itemView.findViewById(R.id.quantity_stb)
            var cardView1 : CardView = itemView.findViewById(R.id.cardinalb)
            var image: ImageView = itemView.findViewById(R.id.icon_compb)
            fun bind(stock: Stock){
                name.setText(stock.companies?.name)
                cost.setText(stock.cost.toString())
                quantity.setText(stock.quantity.toString())
                image.setImageResource(stock.companies?.r!!)
                cardView1.setOnClickListener {
                    val fm =
                        fragmentManager
                    val ft = fm?.beginTransaction()
                    var f =
                        StockFragment.instfragment(
                            stock
                        )
                    ft?.replace(R.id.frame_menu, f)
                    ft?.addToBackStack(null)
                    ft?.commit()
                }
            }
        }
    }
}