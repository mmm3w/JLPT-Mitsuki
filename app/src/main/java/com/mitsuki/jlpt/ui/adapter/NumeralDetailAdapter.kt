package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Word
import io.reactivex.subjects.PublishSubject

class NumeralDetailAdapter : BaseAdapter<Word, NumeralDetailAdapter.MyViewHolder>() {

    val parentSubject: PublishSubject<Word> = PublishSubject.create()

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder =
        MyViewHolder(viewGroup)

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        getItem(i).let {
            t.cnText.text = it.cn
            t.jpText.text = it.jp
            t.kanaText.text = it.kana
            t.voiceText.setOnClickListener { _ -> parentSubject.onNext(it) }
        }
    }

    inner class MyViewHolder(parent: ViewGroup) : AutoDisposeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_word_entry, parent, false)
    ) {
        val jpText: TextView = itemView.findViewById(R.id.text_jp)
        val cnText: TextView = itemView.findViewById(R.id.text_cn)
        val kanaText: TextView = itemView.findViewById(R.id.text_kana)
        val voiceText: ImageView = itemView.findViewById(R.id.text_voice)
    }
}