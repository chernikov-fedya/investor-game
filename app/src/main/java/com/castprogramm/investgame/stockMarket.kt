package com.castprogramm.investgame

import com.castprogramm.investgame.EnumClasses.Error

class stockMarket {
    var markStock = Stoks.allStoks

    fun sold(stock: Stock, k: Int = 1): Error? {
        if (Broker.myStock.size < k)
            return Error.EMPTYBAG
        else{
            Broker.wallet = Broker.wallet.plus(stock.cost * k)
            Broker.myStock.remove(stock)
            return null
        }
    }

    fun buy(stock: Stock, k: Int = 1): Error? {
        if (markStock.size < k)
            return Error.EMPTYMARKET
        if (Broker.wallet < stock.cost * k)
            return Error.NOMONEY
        else{
            Broker.wallet = Broker.wallet.minus(stock.cost * k)
            Broker.myStock.find {it == stock}?.let{it.quantity++} ?:{Broker.myStock.add(stock)}()
            return null
        }

    }
}