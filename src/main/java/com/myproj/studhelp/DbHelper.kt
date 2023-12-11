package com.myproj.studhelp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileInputStream
import java.io.IOException

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "buildings_database"
        const val DATABASE_VERSION = 1
        const val TABLE_BUILDINGS = "buildings"
        const val COLUMN_ID = "_id"
        const val COLUMN_BUILDING_NUMBER = "number"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
        const val PREFS_NAME = "MyPrefsFile"
        const val IS_FIRST_RUN = "isFirstRun"
    }

    private val context: Context = context

    private val CREATE_BUILDINGS_TABLE = ("CREATE TABLE $TABLE_BUILDINGS (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_BUILDING_NUMBER TEXT, " +
            "$COLUMN_LATITUDE DOUBLE, " +
            "$COLUMN_LONGITUDE DOUBLE);")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_BUILDINGS_TABLE)

        val prefs = context.getSharedPreferences(PREFS_NAME, 0)
        val isFirstRun = prefs.getBoolean(IS_FIRST_RUN, true)

        if (isFirstRun) {

            val editor = prefs.edit()
            editor.putBoolean(IS_FIRST_RUN, false)
            editor.apply()
        }

        val values = ContentValues()
        values.put(COLUMN_BUILDING_NUMBER, "Головний корпус")
        values.put(COLUMN_LATITUDE, "dsaf")
        values.put(COLUMN_LONGITUDE, "ASDf")
        db.insert(TABLE_BUILDINGS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        /*db!!.execSQL("DROP TABLE IF EXISTS $TABLE_SUBJECTS")
        onCreate(db)*/
    }


    private fun copyDatabaseFromInternalToAssets() {
        try {
            val inputStream = FileInputStream(context.getDatabasePath(DATABASE_NAME))
            val outputStream = context.assets.openFd(DATABASE_NAME).createOutputStream()

            val buffer = ByteArray(1024)
            var length: Int

            while ((inputStream.read(buffer)).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    fun getAllData(): Cursor {
        val db = readableDatabase
        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_BUILDING_NUMBER,
            COLUMN_LATITUDE,
            COLUMN_LONGITUDE
        )
        return db.query(TABLE_BUILDINGS, projection, null, null, null, null, null)
    }
}