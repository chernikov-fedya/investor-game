package com.castprogramm.investgame

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.concurrent.TimeUnit

class CostView(context: Context, atrr: AttributeSet): GraphView(context, atrr), Up{
    init {
        this.viewport.setScalable(true)
        this.viewport.setScalableY(true)
        this.viewport.scrollToEnd()
    }
    fun addStock(temp: Stock, live:LifecycleOwner){
        var newtest : LiveData<MutableList<DataPoint>> = temp.costsofStock
        newtest.observe(live, androidx.lifecycle.Observer { t ->
            var functiom = LineGraphSeries<DataPoint>(Array(t.size, { t[it] }))
            this.addSeries(functiom)
            this.viewport.scrollToEnd()
            this.viewport.computeScroll() })
    }

    override fun update() {
        this.viewport.scrollToEnd()
    }
}
