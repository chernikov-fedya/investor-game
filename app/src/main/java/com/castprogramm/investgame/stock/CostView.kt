package com.castprogramm.investgame.stock

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.castprogramm.investgame.Up
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
// Класс отрисовки графика, наследуемый от GraphView, с адапртацией под класс Stock
class CostView(context: Context, atrr: AttributeSet): GraphView(context, atrr), Up {
    init {
        this.viewport.isScalable = true // Добавление возмозможности настройки масштаба
        this.viewport.setScalableY(true)
    }
    fun addStock(temp: Stock, live:LifecycleOwner){
        var newtest : LiveData<MutableList<DataPoint>> = temp.costsofStock
        newtest.observe(live, androidx.lifecycle.Observer { t ->
            var functiom = LineGraphSeries<DataPoint>(Array(t.size, { t[it] }))
            this.addSeries(functiom)
            this.viewport.scrollToEnd()
            this.viewport.computeScroll()
            this.viewport.setMinX(0.0)
        })
    }

    override fun update() {
        this.viewport.scrollToEnd()
        this.viewport.setMinX(0.0)
        this.viewport.setMinY(0.0)
    }
}
