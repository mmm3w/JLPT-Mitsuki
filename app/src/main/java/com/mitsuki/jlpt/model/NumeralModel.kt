package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Word

class NumeralModel(private val db: MyDataBase) :BaseModel(){
    fun getNumeralSort():List<NumeralSort>{
        return db.wordDao().queryNumeralSort()
    }

    fun getNumeralDetail(start:Int, total:Int):List<Word>{
        return db.wordDao().queryNumeralDetail(start, total, Kind.NUMERAL)
    }
}