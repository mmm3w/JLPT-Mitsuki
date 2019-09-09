package com.mitsuki.jlpt.entity

data class Setting(val text: String, val ext: Any, val callback: (s: Setting) -> Unit) {
    fun getExtString(): String = ext.toString()
}