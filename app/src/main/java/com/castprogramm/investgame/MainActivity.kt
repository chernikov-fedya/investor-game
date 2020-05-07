package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import kotlinx.android.synthetic.main.activity_main.*

class Test(var text : TextView) : Up{
    var test = 0;
    override fun update() {
        test ++
        text.setText(test.toString())
    }
}
class MainActivity : AppCompatActivity() {
    var handler = Handler()
    var testing = Updater(handler)
    var new = News()
    var broker = Broker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var X = 0
        var Y = 0
        var point : DataPoint
        var graph = findViewById<View>(R.id.graph) as GraphView
        //var test = Stock()
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
        var wow = Test(value)
        testing.objectsToUpdate.add(wow)
        handler.post(testing)
        txt.text = new.msg
    broker.fill()

    ActivityIntent()
    }

    fun ActivityIntent(){
        val handler = android.os.Handler()
        handler.postDelayed({ if (new.eventType == "повышение"){
            for (i in 0..(broker.myStock?.size?.minus(1) ?: -1)){
                broker.myStock!![i].cost = broker.myStock!![i].cost.plus(52.23)
            }
        }
        else{
            for (i in 0..(broker.myStock?.size?.minus(1) ?: -1)){
                broker.myStock!![i].cost = broker.myStock!![i].cost.minus(34.32)
            }
        }
        }, 2000)
    }
}
