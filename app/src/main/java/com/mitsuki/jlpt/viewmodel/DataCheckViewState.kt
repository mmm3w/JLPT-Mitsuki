package com.mitsuki.jlpt.viewmodel
import com.mitsuki.jlpt.app.kind.Kind

data class DataCheckViewState(val kind: Int, val number: Int) {
    fun getKindMode() = Kind.getKind(kind)
}