package com.mitsuki.jlpt.app

import android.annotation.SuppressLint
import android.content.Context

const val SHARED_PREFERENCES_NAME = "SharedPreferences"

@SuppressLint("ApplySharedPref")
fun Context.putString(data: HashMap<String, String>) {
    val editor = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
    for (pv in data) {
        editor.putString(pv.key, pv.value)
    }
    editor.commit()
}

@SuppressLint("ApplySharedPref")
fun Context.putString(key: String, value: String) {
    val editor = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
    editor.putString(key, value)
    editor.commit()
}

fun Context.getString(key: String): String {
    return getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(
        key, ""
    ) ?: ""
}

@SuppressLint("ApplySharedPref")
fun Context.putInt(data: HashMap<String, Int>) {
    val editor = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
    for (pv in data) {
        editor.putInt(pv.key, pv.value)
    }
    editor.commit()
}

@SuppressLint("ApplySharedPref")
fun Context.putInt(key: String, value: Int) {
    val editor = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
    editor.putInt(key, value)
    editor.commit()
}

fun Context.getInt(key: String): Int {
    return getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(
        key, Int.MIN_VALUE
    )
}
