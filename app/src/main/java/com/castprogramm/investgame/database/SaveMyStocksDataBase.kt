package com.castprogramm.investgame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.castprogramm.investgame.stock.Stock

@Database(entities = [DataUserStock::class], version = 1)
abstract class SaveMyStocksDataBase: RoomDatabase() {
    abstract fun getStockDao(): SaveMyStocksDao
}