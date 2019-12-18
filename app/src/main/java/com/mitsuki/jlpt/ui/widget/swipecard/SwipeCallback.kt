package com.mitsuki.jlpt.ui.widget.swipecard

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlin.math.sqrt


class SwipeCallback : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    private val swipeSubject: PublishSubject<SwipeCallbackState> = PublishSubject.create()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeSubject.onNext(SwipeCallbackState(viewHolder, direction))
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val fraction: Double = (pythagorean(dX, dY) / threshold(recyclerView)).coerceAtMost(1.0)

        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child: View = recyclerView.getChildAt(i)
            val level = childCount - i - 1
            if (level > 0) {
                child.scaleX = (1 - SwipeConfig.SCALE_GAP * (level - fraction)).toFloat()
                if (level < SwipeConfig.MAX_SHOW_COUNT - 1) {
                    child.translationY = (SwipeConfig.TRANS_Y_GAP * (level - fraction)).toFloat()
                    child.scaleY = (1 - SwipeConfig.SCALE_GAP * (level - fraction)).toFloat()
                }
            }
        }
    }

    private fun pythagorean(x: Float, y: Float) = sqrt(x * x + y * y).toDouble()

    private fun threshold(recyclerView: RecyclerView) = recyclerView.width * 0.5f

    fun observable(): Observable<SwipeCallbackState> = swipeSubject.hide()

    data class SwipeCallbackState(val viewHolder: RecyclerView.ViewHolder, val direction: Int)
}