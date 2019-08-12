package com.mitsuki.jlpt.db

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mitsuki.jlpt.entity.Numeral
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

@Database(entities = [Word::class, WordState::class, Numeral::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun numeralDao(): NumeralDao
    abstract fun stateDao(): StateDao
}