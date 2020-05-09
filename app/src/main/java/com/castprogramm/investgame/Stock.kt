package com.castprogramm.investgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint
import java.util.*

import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.castprogramm.investgame.EnumClasses.Companies



class Stock: Up {
    var name: String = ""
    var cost: Double = 0.0
    var quantity: Int = 1  //Поменять для корректного отображения прибыли
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

    override fun getItemCount(): Int = stocks.size

    override fun onBindViewHolder(holder: StockVIewHolder, position: Int) {
            holder.bind(stocks[position])
    }

    companion object{
        var fragmentManager : FragmentManager? = null
        class StockVIewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_st)
            var cost: TextView = itemView.findViewById(R.id.cost_st)
            var quantity: TextView = itemView.findViewById(R.id.quantity_st)
            var cardView : CardView = itemView.findViewById(R.id.cardinal)
            fun bind(stock: Stock){
                name.setText(stock.companies?.name)
                cost.setText(stock.cost.toString())
                quantity.setText(stock.quantity.toString())
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