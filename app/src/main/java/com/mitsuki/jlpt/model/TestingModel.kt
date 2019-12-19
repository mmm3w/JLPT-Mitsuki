package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word

class TestingModel(
    private val db: MyDataBase, private val spRepository: SPRepository
) : BaseModel() {
    fun obtainInitialData(): List<Word> {

        return db.wordDao().queryRandomWords(5, 10)
    }
}