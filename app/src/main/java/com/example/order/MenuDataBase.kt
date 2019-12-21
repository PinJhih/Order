package com.example.order

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MenuDataBase(
    context: Context, name: String = database,
    factory: SQLiteDatabase.CursorFactory? = null, version: Int = v
) :

    SQLiteOpenHelper(context, name, factory, version) {
    companion object {
        private const val database = "SchoolFair.db"
        private const val v = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE menu (id text PRIMARY KEY,team integer NOT NULL,name text NOT NULL,amount integer NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS menu")
        onCreate(db)
    }
}