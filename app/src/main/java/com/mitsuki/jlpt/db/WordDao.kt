package com.mitsuki.jlpt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mitsuki.jlpt.entity.Word

@Dao
interface WordDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(words: List<Word>)

    @Query("SELECT * FROM word")
    fun queryWords(): Array<Word>
}
