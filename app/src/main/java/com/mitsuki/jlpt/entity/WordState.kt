package com.mitsuki.jlpt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "word_state", foreignKeys = [ForeignKey(
        entity = Word::class,
        parentColumns = ["id"],
        childColumns = ["word_id"]
)]
)
data class WordState(@PrimaryKey(autoGenerate = true) var id: Int, @ColumnInfo(name = "word_id") var wordID: Int,
                     var fav: Boolean, var visible: Boolean)