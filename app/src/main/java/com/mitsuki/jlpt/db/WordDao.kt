package com.mitsuki.jlpt.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mitsuki.jlpt.entity.Word

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(words: List<Word>)

    @Query("SELECT * FROM word")
    fun queryWords(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE visible IS NULL OR visible=1")
    fun queryWordsWithVisible(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE visible=0")
    fun queryWordsWithInvisible(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=1")
    fun queryWordsWithN1(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=2")
    fun queryWordsWithN2(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=3")
    fun queryWordsWithN3(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=4")
    fun queryWordsWithN4(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=5")
    fun queryWordsWithN5(): DataSource.Factory<Int, Word>

}
