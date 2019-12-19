package com.mitsuki.jlpt.ui.widget.swipecard

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mitsuki.jlpt.app.dp2px
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
        val fraction: Double = (pythagorean(dX, dY) / threshold(recyclerView)).coerceAtMost(1.0)

        val childNumber = recyclerView.childCount - 1
        for (position in 0..childNumber) {
            val child: View = recyclerView.getChildAt(position)
            val level = childNumber - position

            if (level != SwipeConfig.MAX_SHOW_COUNT && level > 0) {
                child.scaleX = (1 - SwipeConfig.SCALE_GAP * (level - fraction)).toFloat()
                child.scaleY = (1 - SwipeConfig.SCALE_GAP * (level - fraction)).toFloat()
                child.translationY =
                    (child.height * SwipeConfig.SCALE_GAP * (level - fraction) / 2 + SwipeConfig.TRANS_Y_GAP * (level - fraction)).toFloat()
            }

            if (position == childNumber){
                child.translationX = dX
                child.translationY = dY
            }
        }
    }

    private fun pythagorean(x: Float, y: Float) = sqrt(x * x + y * y).toDouble()

    private fun threshold(recyclerView: RecyclerView) = recyclerView.width * 0.5f

    fun observable(): Observable<SwipeCallbackState> = swipeSubject.hide()

    data class SwipeCallbackState(val viewHolder: RecyclerView.ViewHolder, val direction: Int)
}