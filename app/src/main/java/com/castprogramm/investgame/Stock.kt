package com.castprogramm.investgame

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.ContactsContract
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint
import java.time.format.DateTimeFormatter
import java.util.*

open class Stock: Up {
    var name: String? = null
    var cost: Double? = null
    var costsofStock : MutableLiveData<MutableList<DataPoint>> = MutableLiveData()
    var costs : MutableList<DataPoint> = mutableListOf()


    override fun update() {
        costs.add(DataPoint(Date().seconds.toDouble() , (100..1000).random().toDouble()))
        costsofStock.value = costs
    }
}

class StockPoint(cost : Double): Stock() {
    var costss: Double = cost
    var date = Date()
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
        class StockVIewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_st)
            var cost: TextView = itemView.findViewById(R.id.cost_st)
            var quantity: TextView = itemView.findViewById(R.id.quantity_st)
            fun bind(stock: Stock){
                name.setText(stock.name)
                cost.setText(stock.cost.toString())
                quantity.setText(stock.toString())

            }

        }

    }
}