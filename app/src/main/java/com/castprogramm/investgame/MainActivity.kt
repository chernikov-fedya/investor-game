package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var X = 0
        var Y = 0
        var point : DataPoint
        var graph = findViewById<View>(R.id.graph) as GraphView
        var test = Stock()
        var plotPoints = arrayOfNulls<DataPoint>(3)
        for (i in 0..2){
            X = i
            Y = 2*i
            point = DataPoint(X.toDouble(), Y.toDouble())
            plotPoints[i] = point
        }
        val functiom = LineGraphSeries<DataPoint>(plotPoints)

        graph.viewport.setScalable(true)
        graph.viewport.setScalableY(true)

        graph.addSeries(functiom)
    }
}
