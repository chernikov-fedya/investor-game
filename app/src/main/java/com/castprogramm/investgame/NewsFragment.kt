package com.castprogramm.investgame

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_all_stock.*
import kotlinx.android.synthetic.main.fragment_all_stock.view.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.fragment_news.view.swipeRefreshLayout

class NewsFragment: Fragment() {
    companion object{
        fun newInstance(excer: MutableList<String>): NewsFragment {
            var temp = NewsFragment()
            temp.recMSG = excer
            return temp
        }
    }
    var recMSG: MutableList<String> =  mutableListOf()
    private lateinit var runnable: Runnable
    var handler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_news, container, false)
        ret.swipeRefreshLayout.setOnRefreshListener {
            // Initialize a new Runnable
            runnable = Runnable {
                // Update the text view text with a random number
                var recycler : RecyclerView = ret.findViewById(R.id.aleksey)
                recycler.adapter = NewsAdapter().apply {
                    msgs = recMSG
                }
                // Hide swipe to refresh icon animation
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 1000.toLong()
            )
        }
        var recycler : RecyclerView = ret.findViewById(R.id.aleksey)
        recycler.adapter = NewsAdapter().apply {
            msgs = recMSG
        }


        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        return ret
    }
}