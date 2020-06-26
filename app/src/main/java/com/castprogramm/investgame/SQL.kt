package com.castprogramm.investgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.Stoks
import com.jjoe64.graphview.series.DataPoint

class dbOpenSQLite(context: Context,
                   factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    fun createStockTable(stock: Stock): String = ("CREATE TABLE ${stock.companies?.name} (KEY_ID INTEGER PRIMARY KEY," +
            "X REAL," +
            "Y REAL );")
//    fun createBrokerTable(): String = ("CREATE TABLE BROKER (KEY_ID INTEGER PRIMARY KEY," +
//            "NAME STRING,"+
//            "MONEY DOUBLE );")
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE $TABLE_NAME (KEY_ID INTEGER PRIMARY KEY," +
                "$COLOUM_COMPANY_NAME TEXT," +
                "$COLOUM_QUANTITY INTEGER," +
                "$COLOUM_CENT REAL );")
        db.execSQL(CREATE_PRODUCTS_TABLE)
        Stoks.allStoks.forEach {
            db.execSQL(createStockTable(it))
        }
//        db.execSQL(createBrokerTable())
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
        fun addStock(stock: MutableMap<Stock, Int>) {
            val db = this.writableDatabase
            db.execSQL("DELETE FROM $TABLE_NAME;")
            for (i in stock.toList()) {
                val values = ContentValues()
                values.put(COLOUM_COMPANY_NAME, i.first.companies?.name)
                values.put(COLOUM_QUANTITY, i.second)
                values.put(COLOUM_CENT, i.first.cost)
                db.insert(TABLE_NAME, null, values)
            }
        }
        fun getAllStock(): Cursor? {
            val db = this.readableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }
        fun addDataPoint(stock: Stock){
            val db = this.writableDatabase
            db.execSQL("DELETE FROM ${stock.companies?.name};")
            for (i in stock.costs){
                val values = ContentValues()
                values.put("X", i.x)
                values.put("Y", i.y)
                db.insert(stock.companies?.name, null, values)
            }
        }
        fun readDataPoint(stock: Stock):Stock{
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${stock.companies?.name}", null)
            if (cursor!=null && cursor.count > 0){
                cursor.moveToFirst()
                stock.costs.add(DataPoint(cursor.getDouble(0) - 1.0, cursor.getDouble(2)))
                while (cursor.moveToNext()){
                    stock.costs.add(DataPoint(cursor.getDouble(0) - 1.0, cursor.getDouble(2)))
                }
            }
            cursor.close()
                return stock
        }
//        fun saveBroker(){
//            val db = this.writableDatabase
//            db.execSQL("DELETE FROM BROKER")
//            val values = ContentValues()
//            values.put("NAME", Broker.name)
//            values.put("MONEY", Broker.wallet)
//            db.insert("BROKER", null, values)
//        }
//        fun readBroker(){
//            val db = this.readableDatabase
//            val cursor = db.rawQuery("SELECT * FROM BROKER", null)
//            if (cursor != null && cursor.count > 0) {
//                cursor.moveToFirst()
//                Broker.name = cursor.getString(1)
//                Broker.wallet = cursor.getDouble(2)
//            }
//            cursor.close()
//        }
        companion object {
            private val DATABASE_NAME = "stockStocksf.db"
            private val DATABASE_VERSION = 1
            val TABLE_NAME = "nameS"
            val COLOUM_QUANTITY = "quantity"
            val COLOUM_CENT = "cent"
            val COLOUM_COMPANY_NAME = "company"
        }
    }