package com.mitsuki.jlpt.ui.widget

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

    //    val path = Path()
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
        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).translationX = 0f
        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).translationZ = 0f
        viewHolder.itemView.translationZ = 0f
        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).clipToOutline = false
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
        val fp = viewHolder.itemView.findViewById<ImageView>(R.id.text_mark).layoutParams as FrameLayout.LayoutParams
        if (dX > 0) {
            fp.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
        } else if (dX < 0) {
            fp.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        }
        viewHolder.itemView.findViewById<ImageView>(R.id.text_mark).layoutParams = fp

        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).translationX = dX
        viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).translationZ = if (dX == 0f) 0f else 4f
        viewHolder.itemView.translationZ = if (dX == 0f) 0f else 6f
        (abs(dX / viewHolder.itemView.width.toFloat()) * 2 * maxRadius).also {
            viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).outlineProvider = MyViewOutlineProvider(if (it > maxRadius) maxRadius else it)
            viewHolder.itemView.findViewById<ConstraintLayout>(R.id.text_bg).clipToOutline = true
        }
    }
}