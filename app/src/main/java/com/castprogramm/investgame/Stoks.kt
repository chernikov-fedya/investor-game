package com.castprogramm.investgame

object Stoks {
    var stocks: MutableList<Stock> = mutableListOf(
        Stock().apply { name = "Акции Fitness-Project" },
        Stock().apply { name = "Акции Google"},
        Stock().apply { name = "Акции Apple"},
        Stock().apply { name = "Акции Samsung"},
        Stock().apply { name = "Акции Xiaomi"},
        Stock().apply { name = "Акции InvesterGame" },
        Stock().apply { name = "Акции Huawei"},
        Stock().apply { name = "Акции Mail.Ru"})
}