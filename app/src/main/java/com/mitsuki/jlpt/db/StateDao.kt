package com.mitsuki.jlpt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mitsuki.jlpt.entity.WordState

@Dao
interface StateDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(state: WordState)
}
