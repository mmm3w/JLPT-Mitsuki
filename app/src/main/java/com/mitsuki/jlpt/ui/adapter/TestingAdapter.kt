package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Word

class TestingAdapter : BaseAdapter<Word, TestingAdapter.MyViewHolder>() {

    override fun onMyCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_testing, parent, false
        )
    )

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.numberView.text = i.toString()
    }

    inner class MyViewHolder(view: View) : AutoDisposeViewHolder(view) {
        val numberView: TextView = itemView.findViewById(R.id.number)
    }

    fun add(data: Word) {
        mData.add(data)
    }

    fun remove(index: Int) {
        mData.removeAt(index)
    }
}