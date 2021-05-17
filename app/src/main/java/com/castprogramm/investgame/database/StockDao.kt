package com.castprogramm.investgame.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.castprogramm.investgame.stock.Stock

@Dao
interface StockDao {
    @Query("SELECT * FROM Stock")
    fun getAllStocks(): LiveData<List<Stock>>

    @Query("SELECT * FROM dbdatapoint WHERE stock_id IS :stock_id")
    fun getDataPointById(stock_id: String): LiveData<List<DBDataPoint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataPoint(dbDataPoint: DBDataPoint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStock(stock: Stock)
}