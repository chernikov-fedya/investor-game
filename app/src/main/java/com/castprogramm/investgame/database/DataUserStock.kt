package com.castprogramm.investgame.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.castprogramm.investgame.stock.Stock

@Entity
class DataUserStock(val nameStock: String = "", val quantity: Int = 0) {
    constructor(pair: Pair<Stock, Int>): this(pair.first.name, pair.second)

    @PrimaryKey(autoGenerate = true)
    var id = 0
}