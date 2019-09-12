package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase

class DataCheckModel(private val db :MyDataBase) :BaseModel(){
    fun getWordNumber(kind:Int):Int{
        return db.wordDao().queryWordNumber(kind)
    }

    fun getNumeralNumber():Int{
        return db.numeralDao().queryWordNumber()
    }

    fun getInvisibleNumber():Int{
        return db.wordDao().queryInvisibleWordNumber()
    }
}