package com.mitsuki.jlpt.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

class MainToolbarBehavior : CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var tag = true

    override fun layoutDependsOn(
        parent: CoordinatorLayout, child: View, dependency: View
    ): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout, child: View, dependency: View
    ): Boolean {
        if (child.height - dependency.y > 0) {
            if (tag) return true
            tag = true
            ObjectAnimator.ofFloat(child, "elevation", child.elevation, 16f).setDuration(200).start()
            ObjectAnimator.ofFloat(child, "alpha", child.alpha, 0.95f).setDuration(200).start()
        } else {
            if (!tag) return true
            tag = false
            ObjectAnimator.ofFloat(child, "elevation", child.elevation, 0f).setDuration(200).start()
            ObjectAnimator.ofFloat(child, "alpha", child.alpha, 1f).setDuration(200).start()
        }
        return true
    }
}