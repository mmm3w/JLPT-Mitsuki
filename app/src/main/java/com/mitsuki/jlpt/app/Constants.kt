package com.mitsuki.jlpt.app
import android.content.Context
object Constants {
    fun dbFile(context: Context): String = "${dbFolder(context)}/jlpt.db"

    fun dbFolder(context: Context): String =
        "${context.getDir("data", Context.MODE_APPEND).absoluteFile}/JLPT"
}