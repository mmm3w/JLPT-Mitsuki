package com.mitsuki.jlpt.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OPEN_READONLY
import android.os.Environment
import android.util.Log
import com.mitsuki.jlpt.db.DataReference
import com.mitsuki.jlpt.entity.Word
import java.io.File


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
            var w = Word()
            w.jp = cursor.getString(cursor.getColumnIndex("japanese"))
            w.cn = cursor.getString(cursor.getColumnIndex("chinese"))
            w.kana = cursor.getString(cursor.getColumnIndex("hiragana"))
            w.rei = cursor.getString(cursor.getColumnIndex("example"))
            w.reiZh = cursor.getString(cursor.getColumnIndex("example_zh"))
            w.kind = 5
            list.add(w)
        }
        Log.e("saf", "数量" + list.size.toString())

        DataReference.wordDao().insert(list)
        DataReference.wordDao().queryRepos()
    }
}