package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.kind.GenericKind
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

class TestingModel(
    private val db: MyDataBase, private val spRepository: SPRepository
) : BaseModel() {

    private val randomWordCacheSize = 5

    fun obtainInitialData(): MutableList<Word> {
        return shuntMethod(
            spRepository.testingDisplayRange, spRepository.testingKindRange, randomWordCacheSize
        )
    }

    fun obtainAdditionalData(): Word? {
        return shuntMethod(
            spRepository.testingDisplayRange, spRepository.testingKindRange, 1
        ).run { if (size > 0) get(0) else null }
    }


    private fun shuntMethod(tDR: Int, tKR: Int, count: Int): MutableList<Word> {
        return when {
            tDR == GenericKind.TESTING_DISPLAY_ALL && tKR == GenericKind.ALL -> return db.wordDao()
                .queryRandomWords(count)
            tDR == GenericKind.TESTING_DISPLAY_VISIBLE && tKR == GenericKind.ALL -> return db.wordDao()
                .queryRandomWordsWithVisible(count)
            tDR == GenericKind.TESTING_DISPLAY_INVISIBLE && tKR == GenericKind.ALL -> return db.wordDao()
                .queryRandomWordsWithInvisible(count)
            tDR == GenericKind.TESTING_DISPLAY_ALL && tKR != GenericKind.ALL -> return db.wordDao()
                .queryRandomWords(tKR, count)
            tDR == GenericKind.TESTING_DISPLAY_VISIBLE && tKR != GenericKind.ALL -> return db.wordDao()
                .queryRandomWordsWithVisible(tKR, count)
            tDR == GenericKind.TESTING_DISPLAY_INVISIBLE && tKR != GenericKind.ALL -> return db.wordDao()
                .queryRandomWordsWithInvisible(tKR, count)
            else -> ArrayList()
        }
    }

    fun saveWordState(state: WordState) {
        db.stateDao().insert(state)
    }
}