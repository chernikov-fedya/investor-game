package com.castprogramm.investgame

import android.widget.Toast

class stockMarket() {
    var markStock = Stoks.allStoks

    fun sold(broker: Broker, stock: Stock, k: Int = 1): Error? {
        if (broker.myStock.size < k)
            return Error.EMPTYBAG
        else{
            broker.wallet = broker.wallet.plus(stock.cost * k)
            broker.myStock.remove(stock)
            return null
        }
    }

    fun buy(broker: Broker, stock: Stock, k: Int = 1): Error? {
        if (markStock.size < k)
            return Error.EMPTYMARKET
        if (broker.wallet < stock.cost * k)
            return Error.NOMONEY
        else{
            broker.wallet = broker.wallet.minus(stock.cost * k)
            broker.myStock.add(stock)
            return null
        }

    }
}