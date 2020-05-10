package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.castprogramm.investgame.EnumClasses.Companies
import kotlinx.android.synthetic.main.activity_main.*
import com.castprogramm.investgame.Stoks.newsarray
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener


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
                Stoks.allStoks = mutableListOf(
                    Stock().apply { cost = 310.13; companies = Companies.Apple },
                    Stock().apply { cost = 59.62; companies = Companies.Intel},
                    Stock().apply { cost = 29.83; companies = Companies.Twitter},
                    Stock().apply { cost = 16.42; companies = Companies.Mailru},
                    Stock().apply { cost = 212.53; companies = Companies.Facebook},
                    Stock().apply { cost = 40.74; companies = Companies.Yandex},
                    Stock().apply { cost = 2379.4; companies = Companies.Amazon},
                    Stock().apply { cost = 282.7; companies = Companies.MasterCard},
                    Stock().apply { cost = 123.2; companies = Companies.IBM},
                    Stock().apply { cost = 203.33; companies = Companies.GazProm},
                    Stock().apply { cost = 4.83; companies = Companies.Lukoil},
                    Stock().apply { cost = 46.14; companies = Companies.CocaCola},
                    Stock().apply { cost = 181.67; companies = Companies.McDonalds},
                    Stock().apply { cost = 184.67; companies = Companies.Microsoft},
                    Stock().apply { cost = 3.01; companies = Companies.Huawei}
                )
                }
            }
        return super.onOptionsItemSelected(item)
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
            bnv.setOnNavigationItemSelectedListener(object : OnNavigationItemSelectedListener {
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
        }

    }


