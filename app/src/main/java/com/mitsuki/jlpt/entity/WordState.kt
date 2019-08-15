package com.mitsuki.jlpt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "word_state")
data class WordState(@PrimaryKey(autoGenerate = false) var sid: Int, var fav: Boolean, var visible: Boolean)