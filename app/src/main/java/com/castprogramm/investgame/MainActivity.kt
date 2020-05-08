package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import androidx.viewpager.widget.ViewPager


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
    var stock = Stock().apply {
        name = "Акции Fitness-Project"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testing.objectsToUpdate.add(stock)
        handler.post(testing)
    }
        fun runProfile(item: MenuItem) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            var f = BrokerFragment.newInstance(mutableListOf(stock))
            ft.replace(R.id.frame_menu, f)
            ft.commit()
        }

        fun runStock(item: MenuItem) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            var f = StockFragment.instfragment(stock)
            ft.replace(R.id.frame_menu, f)
            ft.commit()
    }







//        var graph = findViewById<View>(R.id.graph) as CostView
//        var temp = Stock()

//        temp.name = "Акции Fitness-Project"
//        graph.addStock(temp)

        //namestock.setText(temp.name)

//        var wow = Test(value)
//        testing.objectsToUpdate.add(wow)
//        handler.post(testing)
//    handler.post(testing)
//    txt.text = new.msg
//        handler.post(testing)
//        txt.text = new.msg
   // broker.fill()

    //ActivityIntent()
    }

//    fun ActivityIntent(){
//        val handler = android.os.Handler()
//        handler.postDelayed({ if (new.eventType == "повышение"){
//            for (i in 0..(broker.myStock?.size?.minus(1) ?: -1)){
//                broker.myStock!![i].cost = broker.myStock!![i].cost?.plus(52.23)
//            }
//        }
//        else{
//            for (i in 0..(broker.myStock?.size?.minus(1) ?: -1)){
//                broker.myStock!![i].cost = broker.myStock!![i].cost?.minus(34.32)
//            }
//        }
//        }, 2000)
//    }

