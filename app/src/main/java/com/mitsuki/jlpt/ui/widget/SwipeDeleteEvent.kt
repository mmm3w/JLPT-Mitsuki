package com.mitsuki.jlpt.ui.widget

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.entity.Word
import io.reactivex.subjects.PublishSubject
import kotlin.math.abs


class SwipeDeleteEvent : ItemTouchHelper.Callback() {

    private val maxRadius = 24f

    val onSwipe: PublishSubject<Int> = PublishSubject.create()

    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) =
        makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe.onNext(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg)?.apply {
            translationX = 0f
            translationZ = 0f
            clipToOutline = false
        }
        viewHolder.itemView.translationZ = 0f
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
        viewHolder.itemView.findViewById<ImageView>(R.id.text_mark)?.apply {
            layoutParams = (layoutParams as FrameLayout.LayoutParams).apply {
                if (dX > 0) {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.START
                } else if (dX < 0) {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.END
                }
            }
        }

        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg)?.apply {
            translationX = dX
            translationZ = if (dX == 0f) 0f else 4f
            (abs(dX / viewHolder.itemView.width.toFloat()) * 2 * maxRadius).also {
                outlineProvider = MyViewOutlineProvider(if (it > maxRadius) maxRadius else it)
                clipToOutline = true
            }
        }

        viewHolder.itemView.translationZ = if (dX == 0f) 0f else 6f
    }
}