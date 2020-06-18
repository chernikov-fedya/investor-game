package com.castprogramm.investgame

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.broker.BrokerFragment
import com.castprogramm.investgame.broker.PreferenceBroker
import com.castprogramm.investgame.news.News
import com.castprogramm.investgame.news.NewsFragment
import com.castprogramm.investgame.stock.*
import com.castprogramm.investgame.stock.Stoks.newsarray
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.Files.find


class MainActivity : AppCompatActivity() {

    var handler = Handler()
    var testing = Updater(handler)


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.reset -> {
                // функция обнуления прогресса для новой игры
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
                val intent = intent
                finish()
                startActivity(intent)
            }
            R.id.reference -> {
                // запуск подсказки
                val f = ReferenceFragmemt(URL.REFERENCE)
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frame_menu, f)
                ft.commit()
            }
            R.id.manual ->{
                val f = ReferenceFragmemt(URL.MANUAL)
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frame_menu, f)
                ft.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop(){
        super.onStop()
        testing.play = false
        PreferenceBroker.save(this)
    }


    //    override fun onPause() {
//        super.onPause()
//        val dbhelper = MindOrksDBOpenHelper(this, null)
//        for (i in 0..Broker.myStock.size-1){
//            dbhelper.addStock(Broker.myStock[i])
//        }
//    }
    override fun onResume(){
        super.onResume()
        testing.play = true
        handler.post(testing)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceBroker.load(this)
        testing.play = true
        // обовление класса брокер
        testing.objectsToUpdate.add(Broker)
        // добавление новостей к апдейтеру
        News.fillNews(testing)
        testing.objectsToUpdate.plusAssign(Stoks.allStoks)
        handler.post(testing)
        StockAdapter.fragmentManager = supportFragmentManager
        BrokerAdapter.fragmentManager = supportFragmentManager
        bnv.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    // создание и запуск фрагмента профиля
                    R.id.butProfile -> {
                        val fm = supportFragmentManager
                        val ft = fm.beginTransaction()
                        var f = BrokerFragment.newInstance(
                            Broker.myStock,
                            Broker.name,
                            Broker.wallet,
                            Broker.myStockCost,
                            Broker.loss
                        )
                        BrokerAdapter.fragment = f
                        ft.replace(R.id.frame_menu, f)
                        ft.commit()
                    }
                    // создание и запуск фрагмента всех акций
                    R.id.butStock -> {
                        val fm = supportFragmentManager
                        val ft = fm.beginTransaction()
                        var f = AllStockFragment()
                        StockAdapter.fragment = f
                        ft.replace(R.id.frame_menu, f)
                        ft.commit()
                    }
                    // создание и запуск фрагмента новостей
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
        // при первом запуске создаётся и запускается фрагмент профиля
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var f = BrokerFragment.newInstance(
            Broker.myStock,
            Broker.name,
            Broker.wallet,
            Broker.myStockCost,
            Broker.loss
        )
        BrokerAdapter.fragment = f
        ft.replace(R.id.frame_menu, f)
        ft.commit()
    }
}


