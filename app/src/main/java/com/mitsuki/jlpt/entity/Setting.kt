package com.mitsuki.jlpt.entity

open class Setting(val text: String, val ext: Any? = null) {
    open fun getExtString(): String = ext.toString()
}