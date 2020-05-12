package com.castprogramm.investgame

import androidx.lifecycle.MutableLiveData
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.broker.Broker.wallet
import com.castprogramm.investgame.broker.BrokerFragment


enum class LossEvent{ // Перечисляем все типы расходов
    Family,
    Illness,
    Food,
    Transport,
    Nothing
}
//class Expense{
//    var fragmemt: BrokerFragment = Broker.fragment!!
//    var loss: Double = 0.0
//    var thisSumStock = Broker.thisWallet
//    var allMoney = 0.0
//
//    fun makeLossEvent(): LossEvent=  // получаем случайное событие типа расход и задаем расход
//        when ((0..100).random()) {
//            in 1..15 ->  {
//                thisSumStock.observe(fragmemt, androidx.lifecycle.Observer {
//                    allMoney = it + Broker.thisMyStock.value!!
//                })
//                if ((wallet + allMoney) / 1000 <= 10)
//                    loss = (wallet + allMoney) / 1000
//                else
//                    loss = (wallet + allMoney) / 1000
//                    loss = loss.toInt().toDouble()
//                LossEvent.Illness
//            }
//            in 15..70 ->  {
//                if ((wallet + allMoney) / 1000 <= 10)
//                    loss = (wallet + allMoney) / 1000
//                else
//                    loss = (wallet + allMoney) / 1000
//                loss = loss.toInt().toDouble()
//                LossEvent.Food
//            }
//            in 70..71 ->  LossEvent.Nothing
//            in 72..90 ->  {
//                if ((wallet + allMoney) / 1000 <= 10)
//                    loss = (wallet + allMoney) / 1000
//                else
//                    loss = (wallet + allMoney) / 1000
//                loss = loss.toInt().toDouble()
//                LossEvent.Transport
//            }
//            in 90..100 ->  {
//                if ((wallet + allMoney) / 1000 <= 10)
//                    loss = (wallet + allMoney) / 1000
//                else
//                    loss = (wallet + allMoney) / 1000
//                loss = loss.toInt().toDouble()
//                LossEvent.Family
//            }
//            else -> LossEvent.Nothing
//        }
//
//    init {
//        makeLossEvent()
//    }
//}



