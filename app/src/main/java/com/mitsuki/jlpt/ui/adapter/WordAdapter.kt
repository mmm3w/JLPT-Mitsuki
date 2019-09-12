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
import io.reactivex.subjects.PublishSubject

class WordAdapter : PagedListAdapter<Word, WordAdapter.MyViewHolder>(diffCallback) {

    val parentSubject: PublishSubject<Word> = PublishSubject.create()

    private var markImgRes = R.drawable.ic_visibility_off_black_24dp

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
            holder.jpText.text = it.jp
            holder.kanaText.text = it.kana
            holder.voiceText.setOnClickListener { _ -> parentSubject.onNext(it) }
            holder.markView.setImageResource(markImgRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    inner class MyViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_word_entry, parent, false)) {
        val jpText: TextView = itemView.findViewById(R.id.text_jp)
        val cnText: TextView = itemView.findViewById(R.id.text_cn)
        val kanaText: TextView = itemView.findViewById(R.id.text_kana)
        val voiceText: ImageView = itemView.findViewById(R.id.text_voice)
        val markView :ImageView = itemView.findViewById(R.id.text_mark)
    }

    fun getItemForOut(position: Int): Word? {
        return getItem(position)
    }

    fun setListMode(visible :Boolean){
        markImgRes = if (visible) R.drawable.ic_visibility_off_black_24dp else R.drawable.ic_visibility_black_24dp
    }
}

