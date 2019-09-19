package com.mitsuki.jlpt.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Word

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(words: List<Word>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSort(sort: List<NumeralSort>)

    @Query("SELECT * FROM numeral_sort")
    fun queryNumeralSort(): List<NumeralSort>

    @Query("SELECT * FROM word")
    fun queryWord(): List<Word>

    @Query("SELECT * FROM word WHERE kind=:kind LIMIT :total OFFSET :start")
    fun queryNumeralDetail(start: Int, total: Int, kind: Int): List<Word>

    @Query("SELECT COUNT(*) FROM word WHERE kind=:kind")
    fun queryWordNumber(kind: Int): Int

    @Query("SELECT COUNT(*) FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE visible=0")
    fun queryInvisibleWordNumber(): Int

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind<>-1")
    fun queryWordsWithVisible(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE visible=0")
    fun queryWordsWithInvisible(): DataSource.Factory<Int, Word>

    @Query("SELECT * FROM word LEFT JOIN (SELECT sid,visible FROM WORD_STATE) ON sid=id WHERE (visible IS NULL OR visible=1) AND kind=:kind")
    fun queryWordsWithVisible(kind: Int): DataSource.Factory<Int, Word>

}
