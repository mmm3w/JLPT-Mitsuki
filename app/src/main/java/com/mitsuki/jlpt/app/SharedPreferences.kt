package com.mitsuki.jlpt.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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
/**************************************************************************************************/
private inline fun <T> SharedPreferences.delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defaultValue)!!

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            edit().setter(key ?: property.name, value).apply()
    }

fun SharedPreferences.int(key: String? = null, defValue: Int = 0): ReadWriteProperty<Any, Int> {
    return delegate(key, defValue, SharedPreferences::getInt, SharedPreferences.Editor::putInt)
}

fun SharedPreferences.long(key: String? = null, defValue: Long = 0): ReadWriteProperty<Any, Long> {
    return delegate(key, defValue, SharedPreferences::getLong, SharedPreferences.Editor::putLong)
}

fun SharedPreferences.float(key: String? = null, defValue: Float = 0f): ReadWriteProperty<Any, Float> {
    return delegate(key, defValue, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat)
}

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false): ReadWriteProperty<Any, Boolean> {
    return delegate(key, defValue, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)
}

fun SharedPreferences.stringSet(key: String? = null, defValue: Set<String> = emptySet()): ReadWriteProperty<Any, Set<String>> {
    return delegate(key, defValue, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet)
}

fun SharedPreferences.string(key: String? = null, defValue: String = ""): ReadWriteProperty<Any, String> {
    return delegate(key, defValue, SharedPreferences::getString, SharedPreferences.Editor::putString)
}