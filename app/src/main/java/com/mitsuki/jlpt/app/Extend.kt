package com.mitsuki.jlpt.app

import android.content.Context
import kotlin.math.roundToInt

fun Context.dp2px(dpValue: Int) = resources.displayMetrics.density * dpValue + 0.5F

fun Context.px2dp(pxValue: Float) = pxValue / resources.displayMetrics.density + 0.5F