package com.mitsuki.jlpt.viewmodel
import com.mitsuki.jlpt.app.kind.getKind

data class DataCheckViewState(val kind: Int, val number: Int) {
    fun getKindMode() = getKind(kind)
}