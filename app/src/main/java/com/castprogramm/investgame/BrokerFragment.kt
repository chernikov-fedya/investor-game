package com.castprogramm.investgame

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
        fun newInstance(excer: MutableList<Stock>, name: String, wallet : Double): BrokerFragment {
            var temp = BrokerFragment()
            temp.recStocks = excer
            temp.name = name
            temp.wallet = wallet
            return temp
        }
    }

    var recStocks: MutableList<Stock> =  mutableListOf()
    var name = String()
    var wallet : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_broker, container, false)
        var recycler : RecyclerView = ret.findViewById(R.id.rec)
        recycler.adapter = StockAdapter().apply {
            stocks = recStocks
        }

        var nameBro : TextView = ret.findViewById(R.id.name)
        var walletBro : TextView = ret.findViewById(R.id.wallet)
        nameBro.setText(name)
        walletBro.setText(wallet.toString())
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        return ret
    }
}