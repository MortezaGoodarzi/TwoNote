package com.example.twonote.data.local.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        const val DB_NAME = "notes.db"
        const val DB_VERSION = 1

        const val NOTES_TABLE = "Notes"
        const val NOTES_ID = "id"
        const val NOTES_TITLE = "title"
        const val NOTES_DETAILS = "details"
        const val NOTES_DELETE_STATE = "deleteState"
        const val NOTES_DATE = "date"


        const val TRUE_STATE = "1"
        const val FALSE_STATE = "0"




    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $NOTES_TABLE(" +
                "$NOTES_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTES_TITLE VARCHAR(255)," +
                "$NOTES_DETAILS TEXT," +
                "$NOTES_DELETE_STATE VARCHAR(1)," +
                "$NOTES_DATE VARCHAR(255))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}