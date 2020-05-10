package com.castprogramm.investgame

import com.castprogramm.investgame.EnumClasses.Error

class stockMarket {
    var markStock = Stoks.allStoks

    fun sold(stock: Stock, k: Int): Error? {
        if (Broker.myStock.find { it == stock } == null)
            return Error.NOSTOCK
        if (Broker.myStock.find { it == stock }?.quantity!! < k)
            return Error.EMPTYBAG
        else{
            Broker.wallet = Broker.wallet.plus(stock.cost * k)
            Broker.myStock.find { it == stock }!!.quantity -= k
            return null
        }
    }

    fun buy(stock: Stock, k: Int): Error? {
        if (markStock.size < k)
            return Error.EMPTYMARKET
        if (Broker.wallet < stock.cost * k)
            return Error.NOMONEY
        else{
            Broker.wallet = Broker.wallet.minus(stock.cost * k)
            Broker.myStock.find {it == stock}?.let{it.quantity += k } ?:{Broker.myStock.add(stock.apply { quantity += k })}()
            return null
        }

    }
}