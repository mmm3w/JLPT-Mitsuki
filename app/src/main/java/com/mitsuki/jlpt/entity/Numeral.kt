package com.mitsuki.jlpt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeral")
class Numeral {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var jp: String = ""
    var cn: String = ""
    var kana: String = ""

    var kind: Int = 0
}
