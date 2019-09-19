package com.mitsuki.jlpt.app.constants

import android.content.Context

object Constants {
    fun dbFile(context: Context): String = "${dbFolder(context)}/jlpt.db"

    fun dbFolder(context: Context): String =
        "${context.getDir("data", Context.MODE_APPEND).absoluteFile}/JLPT"

    const val UPDATE_VERSION =
        "https://raw.githubusercontent.com/MitsukiNIBAN/JLPT-Mitsuki/master/update/version"

    const val UPDATE_FILE =
        "https://raw.githubusercontent.com/MitsukiNIBAN/JLPT-Mitsuki/master/update/words"
}