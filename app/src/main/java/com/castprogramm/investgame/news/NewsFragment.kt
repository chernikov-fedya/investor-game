package com.castprogramm.investgame.news

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.R
import com.castprogramm.investgame.Updater
import kotlinx.android.synthetic.main.fragment_news.*
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
    var testing = Updater(handler)
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
                recycler.adapter = NewsAdapter()
                    .apply {
                    msgs = recMSG
                }
                // Hide swipe to refresh icon animation
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 500.toLong()
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