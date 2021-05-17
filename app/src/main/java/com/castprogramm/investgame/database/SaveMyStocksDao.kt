package com.castprogramm.investgame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SaveMyStocksDao {

    @Query("SELECT * FROM DataUserStock")
    fun getAllMyStocks(): LiveData<List<DataUserStock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStock(dataUserStock: DataUserStock)
}