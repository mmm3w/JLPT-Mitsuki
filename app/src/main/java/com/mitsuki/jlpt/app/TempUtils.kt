package com.mitsuki.jlpt.app

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OPEN_READONLY
import android.os.Environment
import android.util.Log
import androidx.room.Room
import com.mitsuki.jlpt.app.constants.Constants
import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Word
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.WatchEvent


object TempUtils {
    @SuppressLint("NewApi")
    fun getData(context: Context) {

        var jlpt1 = SQLiteDatabase.openDatabase(
            Environment.getExternalStorageDirectory().path + "/jlpt1.sqlite",
            null,
            OPEN_READONLY,
            null
        )

        var jlpt = SQLiteDatabase.openDatabase(
            Environment.getExternalStorageDirectory().path + "/jlpt.sqlite",
            null,
            OPEN_READONLY,
            null
        )
        var list = ArrayList<Word>()

        val cursor1 = jlpt1.rawQuery("select * from words", arrayOf())
        while (cursor1.moveToNext()) {
            val w = Word(
                0,
                cursor1.getString(cursor1.getColumnIndex("japanese")),
                cursor1.getString(cursor1.getColumnIndex("chinese")),
                cursor1.getString(cursor1.getColumnIndex("hiragana")),
                Kind.N1
            )
            list.add(w)
        }

        val cursor = jlpt.rawQuery("select * from words", arrayOf())
        var kindNumber = Kind.N2

        while (cursor.moveToNext()) {

            if (list.size == 857) {
                kindNumber = Kind.N2
            }
            if (list.size == 2688) {
                kindNumber = Kind.N3
            }

            if (list.size == 4491) {
                kindNumber = Kind.N4
            }
            if (list.size == 5125) {
                kindNumber = Kind.N5
            }

            val w = Word(
                0,
                cursor.getString(cursor.getColumnIndex("kanji")),
                cursor.getString(cursor.getColumnIndex("simplified_chinese")),
                cursor.getString(cursor.getColumnIndex("hiragana")),
                kindNumber
            )
            list.add(w)
        }

        Log.e("saf", "数量" + list.size.toString())
        Room.databaseBuilder(context, MyDataBase::class.java, Constants.dbFile(context))
            .allowMainThreadQueries().build().wordDao().insert(list)
    }

    fun getNumeral(context: Context) {
        val numeral = File(Environment.getExternalStorageDirectory().path + "/numeral")

        val numTitle = ArrayList<NumeralSort>()
        val numItem = ArrayList<Word>()
        var tempSort: NumeralSort? = null


        try {
            val fileInputStream = FileInputStream(numeral)
            val inputReader = InputStreamReader(fileInputStream)
            BufferedReader(inputReader).use {
                var line: String
                while (true) {
                    line = it.readLine() ?: break

                    if (line.contains("#")) {
                        tempSort?.let { num ->
                            num.end = numItem.size - 1
                            num.total = numItem.size - num.start
                            numTitle.add(num)
                        }

                        tempSort = NumeralSort(0, line.replace("#", ""), numItem.size, 0, 0)
                    } else {
                        val s = line.split("|")
                        val word = Word(0, s[2], s[0], s[1], Kind.NUMERAL)
                        numItem.add(word)
                    }
                }
            }
            fileInputStream.close()
        } catch (e: Exception) {

        }


        tempSort?.let { num ->
            num.end = numItem.size - 1
            num.total = numItem.size - num.start
            numTitle.add(num)
        }

        val dao =  Room.databaseBuilder(context, MyDataBase::class.java, Constants.dbFile(context))
            .allowMainThreadQueries().build()
        dao.wordDao().insert(numItem)
        dao.wordDao().insertSort(numTitle)

    }
}