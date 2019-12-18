package com.mitsuki.jlpt.ui.widget

import android.animation.ObjectAnimator
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class MainToolbarController(private val toolbar: Toolbar) : RecyclerView.OnScrollListener() {

    private var expendTag = false
    private val duration = 200L
    private val elevationUpper = 16F
    private val elevationLower = 0F
    private val alphaUpper = (1F * 0xFF).roundToInt()
    private val alphaLower = (0.95F * 0xFF).roundToInt()

    private var scrollY = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        scrollY += dy
        onViewScroll(scrollY)
    }

    private fun onViewScroll(y: Int) {
        Log.e("asdf", y.toString())
        if (y > 0) {
            if (expendTag) return
            expendTag = true

            ObjectAnimator.ofFloat(toolbar, "elevation", toolbar.elevation, elevationUpper)
                .setDuration(duration).start()

            ObjectAnimator.ofInt(
                toolbar.background.mutate(), "alpha", toolbar.background.mutate().alpha, alphaLower
            ).setDuration(duration).start()

        } else {
            if (!expendTag) return
            expendTag = false

            ObjectAnimator.ofFloat(toolbar, "elevation", toolbar.elevation, elevationLower)
                .setDuration(duration).start()

            ObjectAnimator.ofInt(
                toolbar.background.mutate(), "alpha", toolbar.background.mutate().alpha, alphaUpper
            ).setDuration(duration).start()
        }
    }
}