package com.castprogramm.investgame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.castprogramm.investgame.stock.Stock

@Database(entities = [Stock::class, DBDataPoint::class],version = 1)
abstract class StockDataBase: RoomDatabase() {
    abstract fun getStockDao():StockDao
}