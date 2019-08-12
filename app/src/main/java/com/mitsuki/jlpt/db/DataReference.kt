package com.mitsuki.jlpt.db

import android.content.Context
import androidx.room.Room
import com.mitsuki.jlpt.app.Constants


object DataReference {
    lateinit var data: MyDataBase

    fun init(context: Context) {
        data = Room
            .databaseBuilder(context.applicationContext, MyDataBase::class.java, Constants.DATABASE_FILE)
            .allowMainThreadQueries()
            .build()
    }

    fun wordDao() = data.wordDao()
    fun numeralDao() = data.numeralDao()
    fun stateDao() = data.stateDao()
}