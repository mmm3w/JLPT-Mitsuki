package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Word

data class NumeralViewState(val words: List<Word>?, val numeralSort: List<NumeralSort>?) {
    companion object {
        fun initial(): NumeralViewState {
            return NumeralViewState(words = ArrayList(), numeralSort = null)
        }
    }
}