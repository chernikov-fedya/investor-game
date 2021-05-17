package com.castprogramm.investgame.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.SplashActivity
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.Stoks
import kotlin.system.exitProcess

class EndFragment: Fragment(){
    fun clearStock(){
        Broker.myStock.clear()
        Stoks.updateLiveData(mutableListOf())
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
        startActivity(Intent(this.context, SplashActivity::class.java))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end, container, false)
        val textEnd = view.findViewById<TextView>(R.id.text_end)
        val textWhy = view.findViewById<TextView>(R.id.text_why)
        val textHelp = view.findViewById<TextView>(R.id.text_help)
        val buttonExit = view.findViewById<Button>(R.id.exit)
        val buttonStart = view.findViewById<Button>(R.id.start)
        buttonStart.setOnClickListener {
            clearStock()
        }
        buttonExit.setOnClickListener {
            exitProcess(0)
        }
        textHelp.text = "Внимательнее следите за новостями. Они отражаются на ценах акций."
        textWhy.text = "Вы потеряли последние деньги. Вы слышите стук в дверь от коллекторов. Незавидная судьба вас ждёт. Стук повторился, но на этот раз он был сильнее..."
        textEnd.text = "Вы проиграли!"
        return view
    }
}