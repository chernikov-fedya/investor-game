package com.castprogramm.investgame

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrokerFragment : Fragment() {

    companion object{
        fun newInstance(excer: MutableList<Stock>, name: String, wallet : Double, stockPrice: Double, expenditure: Double): BrokerFragment {
            var temp = BrokerFragment()
            temp.recStocks = excer
            temp.name = name
            temp.wallet = wallet
            temp.stockPrice = stockPrice
            temp.expenditure = expenditure
            return temp
        }
    }

    var recStocks: MutableList<Stock> =  mutableListOf()
    var name = String()
    var wallet : Double = 0.0
    var stockPrice: Double = 0.0
    var expenditure : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_broker, container, false)
        var recycler : RecyclerView = ret.findViewById(R.id.rec)
        recycler.adapter = BrokerAdapter().apply {
            stocks = recStocks
        }

        var nameBro : TextView = ret.findViewById(R.id.name)
        var walletBro : TextView = ret.findViewById(R.id.wallet)
        var stockPriceBro: TextView = ret.findViewById(R.id.stockPrice)
        var minus : TextView = ret.findViewById(R.id.printminus)
        nameBro.setText("Имя: " + name)
        walletBro.setText("Наличные:  " + wallet.toString())
        stockPriceBro.setText("Стоимость моих акций:  " + stockPrice.toString())
        minus.setText("Текущий расход: " + expenditure.toString())
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        return ret
    }
}