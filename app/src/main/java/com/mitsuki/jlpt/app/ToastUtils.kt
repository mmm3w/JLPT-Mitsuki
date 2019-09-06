package com.mitsuki.jlpt.app

import android.content.Context
import android.widget.Toast

inline fun Context.toastShort(value: () -> String) = Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

inline fun Context.toastLong(value: () -> String) = Toast.makeText(this, value(), Toast.LENGTH_LONG).show()
