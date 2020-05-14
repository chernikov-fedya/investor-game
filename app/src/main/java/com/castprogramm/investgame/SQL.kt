package com.castprogramm.investgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.castprogramm.investgame.stock.Stock

class MindOrksDBOpenHelper(context: Context,
                           factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT" + COLOUM_QUANTITY + "TEXT" + COLOUM_CENT + "TEXT" + COLOUM_COMPANY_Name + "TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addStock(stock: Stock) {
        val values = ContentValues()
        values.put(COLUMN_NAME, stock.name)
        values.put(COLOUM_QUANTITY, stock.quantity)
        values.put(COLOUM_CENT, stock.cost)
        values.put(COLOUM_COMPANY_Name, stock.companies?.n)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllStock(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mindorksName.db"
        val TABLE_NAME = "name"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLOUM_QUANTITY = "quantity"
        val COLOUM_CENT = "cent"
        val COLOUM_COMPANY_Name = "company"
    }
}