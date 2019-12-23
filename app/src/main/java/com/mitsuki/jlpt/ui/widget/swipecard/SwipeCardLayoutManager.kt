package com.mitsuki.jlpt.ui.widget.swipecard

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class SwipeCardLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler!!)
        if (itemCount < 1) return
        val bottomPosition = if (itemCount < SwipeConfig.MAX_SHOW_COUNT + 1) {
            itemCount - 1
        } else {
            SwipeConfig.MAX_SHOW_COUNT
        }

        for (position in bottomPosition downTo 0) {
            val child = recycler.getViewForPosition(position)
            addView(child)
            measureChildWithMargins(child, 0, 0)
            val widthSpace = width - getDecoratedMeasuredWidth(child)
            val heightSpace = height - getDecoratedMeasuredHeight(child)
            calculateItemDecorationsForChild(child, Rect())
            layoutDecorated(
                child,
                widthSpace / 2,
                heightSpace / 2,
                widthSpace / 2 + getDecoratedMeasuredWidth(child),
                heightSpace / 2 + getDecoratedMeasuredHeight(child)
            )

            val level = if (position == SwipeConfig.MAX_SHOW_COUNT) position - 1 else position
            if (level > 0) {
                child.scaleX = 1 - SwipeConfig.SCALE_GAP * level
                child.scaleY = 1 - SwipeConfig.SCALE_GAP * level
                child.translationY =
                    getDecoratedMeasuredHeight(child) * SwipeConfig.SCALE_GAP * level / 2 + SwipeConfig.TRANS_Y_GAP * level
            } else {
                child.scaleX = 1F
                child.scaleY = 1F
                child.translationY = 0F
            }
        }

    }
}