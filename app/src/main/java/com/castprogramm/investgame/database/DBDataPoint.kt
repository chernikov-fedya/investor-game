package com.castprogramm.investgame.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.castprogramm.investgame.stock.Stock
import com.jjoe64.graphview.series.DataPoint

@Entity(foreignKeys = [ForeignKey(entity = Stock::class, parentColumns = ["name"], childColumns = ["stock_id"])])
class DBDataPoint(x: Double, y: Double, val stock_id: String = "", @PrimaryKey(autoGenerate = true) val id: Long = 0L): DataPoint(x, y)