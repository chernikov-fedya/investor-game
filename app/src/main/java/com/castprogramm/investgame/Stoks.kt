package com.castprogramm.investgame

object Stoks {
    var allStoks: MutableList<Stock> = mutableListOf(
        Stock().apply { name = "СЫР"; cost = 1352.5},
        Stock().apply { name = "Nokia";cost = 240.2},
        Stock().apply { name = "MOMO";cost = 1400.24},
        Stock().apply { name = "KinderMorgan";cost = 1050.0})
    // зачем этот массив?
    var newsarray: MutableList<String> = mutableListOf()
    var realAllStock: MutableList<Stock> = mutableListOf(
        Stock().apply { name = Companies.Apple.n; cost = Companies.Apple.cent},
        Stock().apply { name = Companies.Intel.n; cost = Companies.Intel.cent }
    )
}