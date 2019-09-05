package com.mitsuki.jlpt.app

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showOperationResult(text: String, btn: String, listener:(view:View) -> Unit) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setAction(btn){ listener.invoke(it) }.show()
}