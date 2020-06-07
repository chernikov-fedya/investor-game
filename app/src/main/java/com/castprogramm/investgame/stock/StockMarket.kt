package com.castprogramm.investgame.stock

import android.os.Build
import android.os.Handler
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.EnumClasses.Error
import com.castprogramm.investgame.Updater

// класс биржи
class StockMarket() {
    // функция продажи k-числа акций
    fun sold(stock: Stock, k: Int): Error? {
        if (Broker.myStock.containsKey(stock)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (Broker.myStock[stock]!! < k)
                    return Error.EMPTYBAG
                else{
                    Broker.wallet = Broker.wallet.plus(stock.cost * k)
                    Broker.myStock.replace(stock, Broker.myStock[stock]!! - k)

                }

            }

        }
        else
            return Error.EMPTYBAG
        return null
    }

    // функция покупки k-числа акций
    fun buy(stock: Stock, k: Int): Error? {
        if (Broker.wallet < stock.cost * k)
            return Error.NOMONEY
        else{
            Broker.wallet = Broker.wallet.minus(stock.cost * k)
            if (Broker.myStock.containsKey(stock)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Broker.myStock.replace(stock, Broker.myStock[stock]!! + k)
                }
            }
            else{
                Broker.myStock.put(stock, k)
            }
            return null
        }
    }
}