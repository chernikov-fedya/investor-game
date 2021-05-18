package com.castprogramm.investgame.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SaveMyStocksDao {

    @Query("SELECT * FROM DataUserStock")
    fun getAllMyStocks(): LiveData<List<DataUserStock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataUserStock: DataUserStock)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(dataUserStock: DataUserStock): Int

    @Transaction
    fun addStock(dataUserStock: DataUserStock){
        val size = update(dataUserStock)
        if (size != 1)
            insert(dataUserStock)
    }
}