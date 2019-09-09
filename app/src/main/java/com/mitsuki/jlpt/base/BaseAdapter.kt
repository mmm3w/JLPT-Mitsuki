package com.mitsuki.jlpt.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

abstract class BaseAdapter<B, T : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_EMPTY = Integer.MIN_VALUE

    private var mData: MutableList<B> = ArrayList()
    private var emptyLayout: Int = -1
    private var useEmpty = false

    override fun getItemCount(): Int =
        if (mData.size <= 0 && useEmpty && emptyLayout != -1) 1 else mData.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (getItemViewType(i) == VIEW_TYPE_EMPTY && emptyLayout != -1 && useEmpty) {
            onCreateEmptyViewHolder(viewGroup, i)
        } else onMyCreateViewHolder(viewGroup, i)
    }

    override fun onBindViewHolder(t: RecyclerView.ViewHolder, i: Int) {
        if (getItemViewType(i) == VIEW_TYPE_EMPTY) {
            onBindEmptyViewHolder(t, i)
        } else {
            onMyBindViewHolder(t as T, i)
        }
    }

    abstract fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int): T

    abstract fun onMyBindViewHolder(t: T, i: Int)

    fun onCreateEmptyViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val emptyView = LayoutInflater.from(viewGroup.context).inflate(emptyLayout, viewGroup, false)
        return object : RecyclerView.ViewHolder(emptyView) {}
    }

    fun onBindEmptyViewHolder(t: RecyclerView.ViewHolder, i: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return if (mData.size <= 0 && useEmpty && emptyLayout != -1) VIEW_TYPE_EMPTY else super.getItemViewType(
            position
        )
    }

    fun setEmptyLayout(emptyLayout: Int) {
        this.emptyLayout = emptyLayout
    }

    fun addAll(data: List<B>) {
        mData.clear()
        mData.addAll(data)
    }

    fun clear() {
        mData.clear()
    }

    fun getItem(position: Int): B {
        return mData[position]
    }

    fun setUseEmpty(useEmpty: Boolean) {
        this.useEmpty = useEmpty
    }
}
