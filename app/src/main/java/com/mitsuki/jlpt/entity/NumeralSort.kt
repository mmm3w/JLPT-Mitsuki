package com.mitsuki.jlpt.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numeral_sort")
data class NumeralSort(
    @PrimaryKey(autoGenerate = true) var id: Int, var title: String,
    var start: Int,
    var end: Int,
    var total: Int
)