package com.mitsuki.jlpt.ui.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.entity.Word

class WordAdapter : PagedListAdapter<Word, WordAdapter.MyViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
                    oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.cnText.text = it.cn
            holder.jpText.text = "${it.jp}(${it.kana})"
            holder.reiText.text = "${it.rei}\n${it.reiZh}"
            holder.reiText.visibility = if (TextUtils.isEmpty(it.rei)) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_word_entry, parent, false)) {
        val jpText: TextView = itemView.findViewById(R.id.text_jp)
        val cnText: TextView = itemView.findViewById(R.id.text_cn)
        val reiText: TextView = itemView.findViewById(R.id.text_rei)
        val voiceText: ImageView = itemView.findViewById(R.id.text_voice)
    }
}

