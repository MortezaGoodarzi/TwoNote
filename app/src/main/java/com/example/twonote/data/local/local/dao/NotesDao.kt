package com.example.twonote.data.local.local.dao

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.model.DBNotesModel
import com.example.twonote.data.model.RecyclerNoteModel

class NotesDao(
    private val db: DBHelper
) {
    private lateinit var cursor: Cursor
    private val contentValues = ContentValues()

    fun saveNotes(notes: DBNotesModel): Boolean {

        val database = db.writableDatabase
        setContentValues(notes)
        val result = database.insert(DBHelper.NOTES_TABLE, null, contentValues)
        database.close()
        return result > 0
    }

    fun getNotesForRecycler(state: String): ArrayList<RecyclerNoteModel> {
        val database = db.readableDatabase
        val query = "SELECT ${DBHelper.NOTES_ID} , ${DBHelper.NOTES_TITLE} " +
                "FROM ${DBHelper.NOTES_TABLE} " +
                "WHERE ${DBHelper.NOTES_DELETE_STATE} = ?"
        cursor = database.rawQuery(query, arrayOf(state))
        val data = getDataForRecycler()
        cursor.close()
        database.close()
        return data
    }

    private fun getDataForRecycler(): ArrayList<RecyclerNoteModel> {

        val data = ArrayList<RecyclerNoteModel>()

        try {
            if (cursor.moveToFirst()) {

                do {
                    val id = cursor.getInt(getIndex(DBHelper.NOTES_ID))
                    val title = cursor.getString(getIndex(DBHelper.NOTES_TITLE))
                    data.add(RecyclerNoteModel(id, title))

                } while (cursor.moveToNext())
            }

        } catch (e: Exception) {
            Log.i("Error", e.message.toString())
        }
        return data

    }

    private fun getIndex(name: String) = cursor.getColumnIndex(name)

    private fun setContentValues(notes: DBNotesModel) {

        contentValues.clear()
        contentValues.put(DBHelper.NOTES_TITLE, notes.title)
        contentValues.put(DBHelper.NOTES_DETAILS, notes.details)
        contentValues.put(DBHelper.NOTES_DELETE_STATE, notes.deleteState)
        contentValues.put(DBHelper.NOTES_DATE, notes.date)

    }

    fun getNoteById(id: Int): DBNotesModel {
        val database = db.readableDatabase
        val query = "SELECT * FROM ${DBHelper.NOTES_TABLE} WHERE ${DBHelper.NOTES_ID} = ?"
        cursor = database.rawQuery(query, arrayOf(id.toString()))
        val data: DBNotesModel = getData()
        cursor.close()
        database.close()
        return data
    }

    private fun getData(): DBNotesModel {
        val data = DBNotesModel(0, "", "", "", "")

        try {

            if (cursor.moveToFirst()) {
                data.id = cursor.getInt(getIndex(DBHelper.NOTES_ID))
                data.title = cursor.getString(getIndex(DBHelper.NOTES_TITLE))
                data.details = cursor.getString(getIndex(DBHelper.NOTES_DETAILS))
                data.deleteState = cursor.getString(getIndex(DBHelper.NOTES_DELETE_STATE))
                data.date = cursor.getString(getIndex(DBHelper.NOTES_DATE))
            }

        } catch (e: Exception) {
            Log.i("ERROR", e.message.toString())
        }
        return data
    }

    fun editNotes(id: Int, state: String): Boolean {

        val database = db.writableDatabase
        contentValues.clear()
        contentValues.put(DBHelper.NOTES_DELETE_STATE, state)
        val result = database.update(
            DBHelper.NOTES_TABLE,
            contentValues,
            "${DBHelper.NOTES_ID} = ?",
            arrayOf(id.toString())
        )
        database.close()
        return result > 0
    }

    fun editNotes(id: Int, note: DBNotesModel): Boolean {

        val database = db.writableDatabase
        setContentValues(note)
        val result = database.update(
            DBHelper.NOTES_TABLE,
        contentValues,
            "${DBHelper.NOTES_ID} = ?",
            arrayOf(id.toString())
        )
        database.close()
        return result > 0
    }

    fun deleteNotes(id: Int): Boolean {

        val database = db.writableDatabase

        val result = database.delete(
            DBHelper.NOTES_TABLE,
            "${DBHelper.NOTES_ID} = ?",
            arrayOf(id.toString())
        )
        database.close()
        return result > 0
    }
}