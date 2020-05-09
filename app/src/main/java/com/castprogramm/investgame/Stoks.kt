package com.castprogramm.investgame

object Stoks {
    var allStoks: MutableList<Stock> = mutableListOf(
        Stock().apply { name = "СЫР"; cost = 1352.5},
        Stock().apply { name = "Nokia";cost = 240.2},
        Stock().apply { name = "MOMO";cost = 1400.24},
        Stock().apply { name = "KinderMorgan";cost = 1050.0})
}