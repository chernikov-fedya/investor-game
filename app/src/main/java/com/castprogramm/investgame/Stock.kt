package com.castprogramm.investgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint
import java.util.*

import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.castprogramm.investgame.EnumClasses.Companies



class Stock: Up {
    var name: String = ""
    var cost: Double = 0.0
    var quantity: Int = 0  //Поменять для корректного отображения прибыли
    var costsofStock : MutableLiveData<MutableList<DataPoint>> = MutableLiveData()
    var costs : MutableList<DataPoint> = mutableListOf()
    var companies: Companies? = null
    override fun update() {
        costs.add(DataPoint(costs.size.toDouble(), cost))
        costsofStock.value = costs
    }
}

class StockAdapter(): RecyclerView.Adapter<StockAdapter.Companion.StockVIewHolder>(){
    var stocks : MutableList<Stock> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVIewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false)
        return StockVIewHolder(
            eee
        )
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = stocks.size

    override fun onBindViewHolder(holder: StockVIewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    companion object{
        var fragmentManager : FragmentManager? = null
        var fragment: AllStockFragment? = null
        class StockVIewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_st)
            var cost: TextView = itemView.findViewById(R.id.cost_st)
            var cardView : CardView = itemView.findViewById(R.id.cardinal)
            var image: ImageView = itemView.findViewById(R.id.icon_comp)
            fun bind(stock: Stock){
                name.setText(stock.companies?.name)
                image.setImageResource(stock.companies?.r!!)
                var newtest1 : LiveData<MutableList<DataPoint>> = stock.costsofStock
                newtest1.observe(fragment!!, androidx.lifecycle.Observer {
                    cost.setText(it.last().y.toString())
                })
                cardView.setOnClickListener {
                    val fm = fragmentManager
                    val ft = fm?.beginTransaction()
                    var f = StockFragment.instfragment(stock)
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
                    val fm = fragmentManager
                    val ft = fm?.beginTransaction()
                    var f = StockFragment.instfragment(stock)
                    ft?.replace(R.id.frame_menu, f)
                    ft?.addToBackStack(null)
                    ft?.commit()
                }
            }
        }
    }
}