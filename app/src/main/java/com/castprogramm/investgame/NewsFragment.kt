package com.castprogramm.investgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsFragment: Fragment() {
    companion object{
        fun newInstance(excer: MutableList<String>): NewsFragment {
            var temp = NewsFragment()
            temp.recMSG = excer
            return temp
        }
    }
    var recMSG: MutableList<String> =  mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_all_stock, container, false)
        var recycler : RecyclerView = ret.findViewById(R.id.aleksey)
        recycler.adapter = NewsAdapter().apply {
            msgs = recMSG
        }

        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        return ret
    }
}