package com.castprogramm.investgame

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import com.castprogramm.investgame.EnumClasses.Companies

import com.castprogramm.investgame.Stoks.newsarray
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var handler = Handler()
    var testing = Updater(handler)

    var mstck: MutableList<Stock> = mutableListOf(
        Stock().apply {name = "СЫР"; cost = 1352.5 },
        Stock().apply {name = "Nokia"; cost = 240.2 },
        Stock().apply { name = "MOMO"; cost = 1400.24 },
        Stock().apply {name = "KinderMorgan"; cost = 1050.0})

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.reset -> {
                Broker.myStock.clear()
                Stoks.newsarray.clear()
                Broker.myStockCost = 0.0
                Broker.wallet = 10000.0

                val intent = intent
                finish()
                startActivity(intent)
                }
            R.id.reference -> {
                val f = ReferenceFragmemt()
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frame_menu, f)
                ft.commit()
            }
            }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        testing.play = false
    }
    override fun onResume() {
        super.onResume()
        testing.play = true
        handler.post(testing)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // обовление класса брокер
        testing.objectsToUpdate.add(Broker)
        // добавление новостей к апдейтеру
        News.fillNews(testing)
            testing.objectsToUpdate.plusAssign(mstck)
            testing.objectsToUpdate.plusAssign(Stoks.allStoks)

        //testing.objectsToUpdate.add(ss)
            handler.post(testing)
        StockAdapter.fragmentManager = supportFragmentManager
        BrokerAdapter.fragmentManager = supportFragmentManager
            bnv.setOnNavigationItemSelectedListener(object :
                BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.butProfile -> {
                            val fm = supportFragmentManager
                            val ft = fm.beginTransaction()
                            var f = BrokerFragment.newInstance(
                               Broker.myStock,
                                Broker.name,
                                Broker.wallet,
                                Broker.myStockCost
                            )
                            ft.replace(R.id.frame_menu, f)
                            ft.commit()
                        }
                        R.id.butStock -> {
                            val fm = supportFragmentManager
                            val ft = fm.beginTransaction()
                            var f = AllStockFragment.newInstance(Stoks.allStoks)
                            ft.replace(R.id.frame_menu, f)
                            ft.commit()
                        }
                        R.id.butNews -> {
                            val fm = supportFragmentManager
                            val ft = fm.beginTransaction()
                            var f = NewsFragment.newInstance(newsarray)
                            ft.replace(R.id.frame_menu, f)
                            ft.commit()
                        }
                    }
                    return true
                }
            })
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var f = BrokerFragment.newInstance(
            Broker.myStock,
            Broker.name,
            Broker.wallet,
            Broker.myStockCost
        )
        ft.replace(R.id.frame_menu, f)
        ft.commit()


        }

    }


