package com.castprogramm.investgame.EnumClasses

import com.castprogramm.investgame.R

enum class Countries(var s : String, var n: Int) {
    USA("США", R.mipmap.usa),
    GreatBritan("Великобритания", R.mipmap.uk),
    China("Китай", R.mipmap.china),
    Russia("Россия", R.mipmap.russia),
    Germany("Германия", R.mipmap.germany)
}