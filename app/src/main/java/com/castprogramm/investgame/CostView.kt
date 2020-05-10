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
    }
    fun addStock(temp: Stock, live:LifecycleOwner){
        var newtest : LiveData<MutableList<DataPoint>> = temp.costsofStock
        newtest.observe(live, androidx.lifecycle.Observer { t ->
            var functiom = LineGraphSeries<DataPoint>(Array(t.size, { t[it] }))
            this.addSeries(functiom)
            this.viewport.scrollToEnd()
            this.viewport.computeScroll()
            this.viewport.maxYAxisSize
            this.viewport.maxXAxisSize
            this.viewport.setMinimalViewport(0.0, this.viewport.getMaxX(true),0.0, this.viewport.getMaxY(true))
            this.viewport.setMinX(0.0)

        })
    }

    override fun update() {
        this.viewport.scrollToEnd()
    }
}
