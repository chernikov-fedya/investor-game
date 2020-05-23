package com.castprogramm.investgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.util.Xml
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.Stoks
import com.jjoe64.graphview.series.DataPoint

class MindOrksDBOpenHelper(context: Context,
                           factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    fun createStockTable(stock: Stock): String = ("CREATE TABLE ${stock.companies?.n} (KEY_ID INTEGER PRIMARY KEY," +
                "X REAL," +
                "Y REAL );")
    fun addDataPoint(stock: Stock, db: SQLiteDatabase){
        db.execSQL("DELETE FROM ${stock.companies?.n};")
        for (i in stock.costs){
        val values = ContentValues()
        values.put("X", i.x)
        values.put("Y", i.y)
        db.insert(stock.companies?.n, null, values)}
    }
    fun readDataPoint(db: SQLiteDatabase, stock: Stock):MutableList<DataPoint>{
        var data = db.query(stock.companies?.n,null,null,null,null,null,null )
        var red = mutableListOf<DataPoint>()
        data?.let {
            if (data.count != 0){
            it.moveToFirst()
            red.add(DataPoint(data.getDouble(1), data.getDouble(2)))
            while (it.moveToNext()){
                red.add(DataPoint(data.getDouble(1), data.getDouble(2)))
            }
        } }
        data.close()
        return red
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE $TABLE_NAME (KEY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLOUM_QUANTITY INTEGER," +
                "$COLOUM_CENT REAL );")


        db.execSQL(CREATE_PRODUCTS_TABLE)
        Stoks.allStoks.forEach {
            db.execSQL(createStockTable(it))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun delete(name: String = TABLE_NAME){
        var db = this.readableDatabase
        db.delete(name, null, null)
    }
    fun addStock(stock: Stock) {
        val values = ContentValues()
        values.put(COLUMN_NAME, stock.companies?.n)
        values.put(COLOUM_QUANTITY, stock.quantity)
        values.put(COLOUM_CENT, stock.cost)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun readDataFromBase(): MutableList<Stock>{
        var db = this.readableDatabase
        var data = db.query(TABLE_NAME, null,null,null,null,null,null)
        Log.d("DATA", data.count.toString())
        var ret = mutableListOf<Stock>()
        if (data.count != 0)
        data?.let {
            it.moveToFirst()
            ret.add(Stock().apply {
                companies = Companies.values().find { it.n == data.getString(1)}
                quantity = data.getInt(2)
                cost = data.getDouble(3)
            })
        while (data.moveToNext()){
            ret.add(Stock().apply {
                companies = Companies.values().find { it.n == data.getString(1)}
                quantity = data.getInt(2)
                cost = data.getDouble(3) })
        }
        }
        data.close()
        return ret
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "stoks.db"

        val TABLE_NAME = "name"
        val COLUMN_NAME = "name1"
        val COLOUM_QUANTITY = "quantity"
        val COLOUM_CENT = "cent"
    }
}