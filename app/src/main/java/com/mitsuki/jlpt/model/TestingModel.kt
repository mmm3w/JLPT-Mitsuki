package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

class TestingModel(
    private val db: MyDataBase, private val spRepository: SPRepository
) : BaseModel() {

    private val randomWordCacheSize = 5

    fun obtainInitialData(): MutableList<Word> {
        return when (spRepository.testingDisplayRange) {
            Kind.TESTING_DISPLAY_ALL -> db.wordDao().queryRandomWords(
                spRepository.wordKind, randomWordCacheSize
            )
            Kind.TESTING_DISPLAY_VISIBLE -> db.wordDao().queryRandomWordsWithVisible(
                spRepository.wordKind, randomWordCacheSize
            )
            Kind.TESTING_DISPLAY_INVISIBLE -> db.wordDao().queryRandomWordsWithInvisible(
                spRepository.wordKind, randomWordCacheSize
            )
            else -> ArrayList()
        }
    }

    fun obtainAdditionalData(): Word? {
        return when (spRepository.testingDisplayRange) {
            Kind.TESTING_DISPLAY_ALL -> db.wordDao().queryRandomWords(spRepository.wordKind, 1)
            Kind.TESTING_DISPLAY_VISIBLE -> db.wordDao().queryRandomWordsWithVisible(
                spRepository.wordKind, 1
            )
            Kind.TESTING_DISPLAY_INVISIBLE -> db.wordDao().queryRandomWordsWithInvisible(
                spRepository.wordKind, 1
            )
            else -> ArrayList()
        }.run {
            if (size > 0) {
                get(0)
            } else {
                null
            }
        }
    }

    fun saveWordState(state: WordState) {
        db.stateDao().insert(state)
    }
}