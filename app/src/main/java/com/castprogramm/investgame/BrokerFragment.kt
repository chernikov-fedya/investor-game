package com.castprogramm.investgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrokerFragment : Fragment() {

    companion object{
        fun newInstance(excer: MutableList<Stock>): BrokerFragment {
            var temp = BrokerFragment()
            temp.recStocks = excer
            return temp
        }
    }

    var recStocks: MutableList<Stock> =  mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_broker, container, false)
        var recycler : RecyclerView = ret.findViewById(R.id.rec)
        recycler.adapter = StockAdapter().apply {
            stocks = recStocks
        }
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        return ret
    }
}