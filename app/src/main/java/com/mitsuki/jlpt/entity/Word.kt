package com.mitsuki.jlpt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(@PrimaryKey(autoGenerate = true) var id: Int, var jp: String, var cn: String,
                var kana: String, var rei: String, @ColumnInfo(name = "rei_zh") var reiZh: String,
                var kind: Int)