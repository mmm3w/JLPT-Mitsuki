package com.mitsuki.jlpt.app

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OPEN_READONLY
import android.os.Environment
import android.util.Log
import androidx.room.Room
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word


object TempUtils {
    @SuppressLint("NewApi")
    fun getData(context: Context) {

        var da =
            SQLiteDatabase.openDatabase(
                Environment.getExternalStorageDirectory().path + "/jlpt5.sqlite",
                null,
                OPEN_READONLY,
                null
            )

        val cursor = da.rawQuery("select * from words", arrayOf())

        var list = ArrayList<Word>()

        while (cursor.moveToNext()) {
            var w = Word(
                0,
                cursor.getString(cursor.getColumnIndex("japanese")),
                cursor.getString(cursor.getColumnIndex("chinese")),
                cursor.getString(cursor.getColumnIndex("hiragana")),
                cursor.getString(cursor.getColumnIndex("example")),
                cursor.getString(cursor.getColumnIndex("example_zh")),
                5
            )
            list.add(w)
        }
        Log.e("saf", "数量" + list.size.toString())
        Room.databaseBuilder(context, MyDataBase::class.java, Constants.DATABASE_FILE)
            .allowMainThreadQueries()
            .build().wordDao().insert(list)
    }
}