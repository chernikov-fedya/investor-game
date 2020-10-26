package com.castprogramm.investgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.broker.BrokerFragment
import com.castprogramm.investgame.broker.PreferenceBroker
import com.castprogramm.investgame.news.News
import com.castprogramm.investgame.news.NewsFragment
import com.castprogramm.investgame.stock.*
import com.castprogramm.investgame.stock.Stoks.allMax
import com.castprogramm.investgame.stock.Stoks.allStoks
import com.castprogramm.investgame.stock.Stoks.newsarray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

//    val scope = CoroutineScope(Job() + Dispatchers.Main)

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
                Stoks.updateLiveData(mutableListOf())
                Broker.myStockCost = 0.0
                Broker.wallet = 10000.0
                allStoks = mutableListOf(
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
                val f = ReferenceFragment(URL.REFERENCE)
                flip(f)
            }
            R.id.manual ->{
                val f = ReferenceFragment(URL.MANUAL)
                flip(f)
            }
            R.id.teach ->{
                val prefEditorStock = StockFragment.prefManagerStock.edit()
                prefEditorStock.putBoolean("didShowStockPrompt", false)
                prefEditorStock.apply()
                val prefEditorNews = NewsFragment.prefManagerNews.edit()
                prefEditorNews.putBoolean("didShowNewsPrompt", false)
                prefEditorNews.apply()
                val prefEditorAllStock = AllStockFragment.prefManagerAllStock.edit()
                prefEditorAllStock.putBoolean("didShowAllStockPrompt", false)
                prefEditorAllStock.apply()
                val prefEditorBroker = BrokerFragment.prefManagerBroker.edit()
                prefEditorBroker.putBoolean("didShowBrokerPrompt", false)
                prefEditorBroker.apply()
            }
            }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop(){
        super.onStop()
        testing.play = false
        val saveIntent = Intent(this, SaveService ::class.java)
        startService(saveIntent)
        PreferenceBroker.save(this)
    }

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
//        val job = scope.launch {
//            val result = async {
//                val dbhadler = DBOpenSQLite(this@MainActivity, null)
//                val cursor = dbhadler.getAllStock()
//                if (cursor!=null && cursor.count > 0){
//                    cursor.moveToFirst()
//                    Broker.myStock.put(
//                        allStoks.find { it.companies?.name ==  cursor.getString(cursor.getColumnIndex(DBOpenSQLite.COLOUM_COMPANY_NAME))}.apply {
//                            this!!.cost = cursor.getDouble(cursor.getColumnIndex(DBOpenSQLite.COLOUM_CENT)) }!!,
//                        cursor.getInt(cursor.getColumnIndex(DBOpenSQLite.COLOUM_QUANTITY)))
//                    while (cursor.moveToNext()){
//                        Broker.myStock.put(
//                            allStoks.find { it.companies?.name ==  cursor.getString(cursor.getColumnIndex(DBOpenSQLite.COLOUM_COMPANY_NAME))}.apply {
//                                this!!.cost = cursor.getDouble(cursor.getColumnIndex(DBOpenSQLite.COLOUM_CENT)) }!!,
//                            cursor.getInt(cursor.getColumnIndex(DBOpenSQLite.COLOUM_QUANTITY)))}
//                    cursor.close()}
//                allStoks.forEach {
//                    dbhadler.readDataPoint(it)
//                    Log.d("SIZE", it.companies?.name + " ${it.costs.size}")
//                }
//                allMax = dbhadler.getMaxSize()
//                dbhadler.close()
//
//            }.await()
//        }
        Broker.thisEnd.observe(this, androidx.lifecycle.Observer{
            if (it <= 0.0)
                endGame()
        })
        // обовление класса брокер
        testing.objectsToUpdate.add(Broker)
        // добавление новостей к апдейтеру
        News.fillNews(testing)
        testing.objectsToUpdate.plusAssign(allStoks)
        handler.post(testing)
            bnv.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    // создание и запуск фрагмента профиля
                    R.id.butProfile -> {
                        flip(installBrokerFragment())
                    }
                    // создание и запуск фрагмента всех акций
                    R.id.butStock -> flip(ActiveFragment())
                    // создание и запуск фрагмента новостей
                    R.id.butNews -> flip(NewsFragment.newInstance(newsarray))
                }
                true
            }
        // при первом запуске создаётся и запускается фрагмент профиля
        flip(installBrokerFragment())
    }
    private fun flip(fragment: Fragment){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_menu, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    private fun installBrokerFragment(): BrokerFragment = BrokerFragment.newInstance(
            Broker.myStock.keys.toMutableList(),
            Broker.name,
            Broker.wallet,
            Broker.myStockCost,
            Broker.loss
        )

    fun endGame(){
        flip(EndFragment())
    }
}