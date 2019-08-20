package com.mitsuki.jlpt.ui.widget

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * view绘制圆角
 */
class MyViewOutlineProvider(private val radius: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        outline?.setRoundRect(0, 0, (view ?: return).width, view.height, radius)
    }
}