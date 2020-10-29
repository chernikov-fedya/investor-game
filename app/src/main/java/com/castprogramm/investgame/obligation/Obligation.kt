package com.castprogramm.investgame.obligation

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.Up
import com.castprogramm.investgame.broker.BrokerFragment
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.StockFragment
import com.castprogramm.investgame.stock.Stoks
import com.jjoe64.graphview.series.DataPoint

class Obligation: Up {
    var cost = 0.0
    var companies: Companies? = null
    var costs = mutableListOf<DataPoint>()
    var costsLiveData : MutableLiveData<MutableList<DataPoint>> = MutableLiveData()

    override fun update() {
        costs.add(DataPoint(costs.size.toDouble(), cost))
        costsLiveData.value = costs
    }

    override fun equals(other: Any?): Boolean =
        if(other is Stock)
            other.companies!!.n == this.companies!!.n
        else
            false

}

class ObligationAdapter(): RecyclerView.Adapter<ObligationAdapter.Companion.ObligationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObligationViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false)
        return ObligationViewHolder(eee)
    }

    override fun getItemCount(): Int = Stoks.allObligation.size

    override fun onBindViewHolder(holder: ObligationViewHolder, position: Int) {
        holder.bind(Stoks.allObligation[position])
    }
    companion object{
        var fragmentManager : FragmentManager? = null // Переменная для передачи supportFragmentManager из MainActivity
        var fragment: Fragment? = null // Переменная для передачи жизненного цикла фрагмента из MainActivity
        class ObligationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.name_st) // Переменная для хранения TextView для вывода названия из файла разметки
            var cost: TextView = itemView.findViewById(R.id.cost_st) // Переменная для хранения TextView для вывода стоимости из файла разметки
            var cardView : CardView = itemView.findViewById(R.id.cardinal) // Переменная для хранения CardView для отрисовки нового фрагмента при нажатии из файла разметки
            var image: ImageView = itemView.findViewById(R.id.icon_comp) // Переменная для хранения ImageView для вывода эмблемы компании, которой принадлежит акция из файла разметки
            var newCost : MutableLiveData<MutableList<DataPoint>>? = null // Переменная для последующего присвоения в неё LiveData
            fun bind(obligation: Obligation){
                name.text = obligation.companies!!.n
                cost.text = "$ %2.f".format(obligation.cost)
                image.setImageResource(obligation.companies!!.r)
                newCost = obligation.costsLiveData
                newCost?.observe(fragment!!, androidx.lifecycle.Observer {
                    cost.text = "$ %2.f".format(it.last().y)
                })
                cardView.setOnClickListener {
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.frame_menu, StockFragment.instfragment(obligation))
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}