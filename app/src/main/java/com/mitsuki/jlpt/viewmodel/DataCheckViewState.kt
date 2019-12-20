package com.mitsuki.jlpt.viewmodel
import com.mitsuki.jlpt.app.kind.KindFactory

data class DataCheckViewState(val kind: Int, val number: Int) {
    fun getKindMode() = KindFactory.getKind(kind)
}