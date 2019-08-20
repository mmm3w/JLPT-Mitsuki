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
import com.mitsuki.jlpt.entity.Numeral
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import io.reactivex.subjects.PublishSubject

class NumeralAdapter : PagedListAdapter<Numeral, NumeralAdapter.MyViewHolder>(diffCallback) {

    val parentSubject: PublishSubject<Numeral> = PublishSubject.create()

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Numeral>() {
            override fun areItemsTheSame(oldItem: Numeral, newItem: Numeral): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Numeral, newItem: Numeral): Boolean =
                oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.cnText.text = it.cn
            holder.jpText.text = "${it.jp}(${it.kana})"
            holder.reiText.visibility = View.GONE
            holder.voiceText.setOnClickListener { _ -> parentSubject.onNext(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    inner class MyViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_word_entry, parent, false)) {
        val jpText: TextView = itemView.findViewById(R.id.text_jp)
        val cnText: TextView = itemView.findViewById(R.id.text_cn)
        val reiText: TextView = itemView.findViewById(R.id.text_rei)
        val voiceText: ImageView = itemView.findViewById(R.id.text_voice)
    }

}

