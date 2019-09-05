package com.mitsuki.jlpt.app

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class MyLinearSmoothScroller(context: Context?) : LinearSmoothScroller(context){
    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics) :Float{
        return 200f / displayMetrics.densityDpi
    }
}