package com.mitsuki.jlpt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

@Database(entities = [Word::class, WordState::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun stateDao(): StateDao
}