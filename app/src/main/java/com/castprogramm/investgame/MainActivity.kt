package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.AdapterView
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.fragment_all_stock.*
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior as Behavior1


class MainActivity : AppCompatActivity() {
    var handler = Handler()
    var testing = Updater(handler)
    //var new = News()
    var broker = Broker()

    var stock = Stock().apply {
        name = "Акции Fitness-Project"
    }
    var mstck: MutableList<Stock> = mutableListOf(Stock().apply {
        name = "СЫР"; cost = 1352.5}, Stock().apply { name = "Nokia"
        cost = 240.2}, Stock().apply { name = "MOMO"
        cost = 1400.24}, Stock().apply { name = "KinderMorgan"
        cost = 1050.0})
    var stckgroup: StockGroup =  StockGroup().apply { name = "Техника"; grouplist = mutableListOf(mstck[0], mstck[1], mstck[2])  }

    var ss: Country  = Country().apply { arrayStockGroup.add(stckgroup)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testing.objectsToUpdate.plusAssign(Stoks.stocks)
        testing.objectsToUpdate.add(stock)
        testing.objectsToUpdate.add(ss)
        handler.post(testing)
        StockAdapter.activity = supportFragmentManager

    }
        fun runProfile(item: MenuItem) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            var f = BrokerFragment.newInstance(Stoks.stocks)
            ft.replace(R.id.frame_menu, f)
            ft.commit()
        }

        fun runStock(item: MenuItem) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            var f = AllStockFragment.newInstance(Stoks.stocks)
            ft.replace(R.id.frame_menu, f)
            ft.commit()
    }
        fun runNews(item: MenuItem){

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

