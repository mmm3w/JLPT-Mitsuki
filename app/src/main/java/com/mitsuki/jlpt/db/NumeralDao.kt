package com.mitsuki.jlpt.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mitsuki.jlpt.entity.Numeral

@Dao
interface NumeralDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(words: List<Numeral>)

    @Query("SELECT * FROM numeral")
    fun queryWords(): DataSource.Factory<Int, Numeral>

    @Query("SELECT COUNT(*) FROM numeral")
    fun queryWordNumber(): Int
}