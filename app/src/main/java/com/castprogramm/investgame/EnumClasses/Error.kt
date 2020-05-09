package com.castprogramm.investgame.EnumClasses

enum class Error(var s: String) {
    NOMONEY("не хватает денег"),
    EMPTYMARKET("На бирже не хватает акций"),
    EMPTYBAG("У вас не хватает акций")
}