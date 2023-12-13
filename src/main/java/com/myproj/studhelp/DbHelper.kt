package com.myproj.studhelp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "tasks_database"
        const val DATABASE_VERSION = 1
        const val TABLE_TASKS = "tasks"
        const val COLUMN_ID = "_id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_TASK_LABEL = "task"

    }

    private val context: Context = context

    private val CREATE_BUILDINGS_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_TASKS (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_STATUS BOOLEAN, " +
            "$COLUMN_TASK_LABEL TEXT);")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_BUILDINGS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun insertTask(task: Task): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_STATUS, task.status)
        values.put(COLUMN_TASK_LABEL, task.taskLabel)

        return db.insert(TABLE_TASKS, null, values)
    }

    fun updateTask(task: Task): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STATUS, 1)
            put(COLUMN_TASK_LABEL, task.taskLabel)
        }

        return db.update(
            TABLE_TASKS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(task.id.toString())
        )
    }


    fun deleteTask(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_TASKS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun getAllData(): Cursor {
        val db = readableDatabase
        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_STATUS,
            COLUMN_TASK_LABEL
        )
        return db.query(TABLE_TASKS, projection, null, null, null, null, null)
    }
}
