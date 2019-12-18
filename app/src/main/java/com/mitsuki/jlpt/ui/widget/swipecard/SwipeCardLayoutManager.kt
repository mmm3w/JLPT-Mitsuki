package com.mitsuki.jlpt.ui.widget.swipecard

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


class SwipeCardLayoutManager(private val mContext: Context) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        //清空所有view
        detachAndScrapAttachedViews(recycler!!)
        if (itemCount < 1) return
        //确定最底部view
        val bottomPosition = if (itemCount < SwipeConfig.MAX_SHOW_COUNT) {
            0
        } else {
            itemCount - SwipeConfig.MAX_SHOW_COUNT
        }

        for (position in 0..bottomPosition) {
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

            //            if (position > 0) {
            //                child.scaleX = 1 - SwipeConfig.SCALE_GAP * position
            //                child.translationY = mContext.dp2px(SwipeConfig.TRANS_Y_GAP) * position
            //                child.scaleY = 1 - SwipeConfig.SCALE_GAP * position
            ////                if (position < SwipeConfig.MAX_SHOW_COUNT - 1) {
            ////                    child.translationY = mContext.dp2px(SwipeConfig.TRANS_Y_GAP) * position
            ////                    child.scaleY = 1 - SwipeConfig.SCALE_GAP * position
            ////                } else {
            ////                    child.translationY = mContext.dp2px(SwipeConfig.TRANS_Y_GAP) * (position - 1)
            ////                    child.scaleY = 1 - SwipeConfig.SCALE_GAP * (position - 1)
            ////                }
            ////            }
            //            val level = itemCount - position - 1
            //            if (level > 0) {
            //                child.scaleX = 1 - SwipeConfig.SCALE_GAP * level
            //                if (level < SwipeConfig.MAX_SHOW_COUNT - 1) {
            //                    child.translationY = (SwipeConfig.TRANS_Y_GAP * level).toFloat()
            //                    child.scaleY = 1 - SwipeConfig.SCALE_GAP * level
            //                } else {
            //                    child.translationY = (SwipeConfig.TRANS_Y_GAP * (level - 1)).toFloat()
            //                    child.scaleY = 1 - SwipeConfig.SCALE_GAP * (level - 1)
            //                }
            //            } else {
            //                child.scaleX = 1f
            //                child.translationY = 0f
            //                child.scaleY = 1f
            //            }
        }

    }
}