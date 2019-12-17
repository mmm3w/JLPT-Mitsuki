package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Setting
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class DefaultSettingAdapter : BaseAdapter<Setting, DefaultSettingAdapter.MyViewHolder>() {

    private val subject: PublishSubject<Setting> by lazy { PublishSubject.create<Setting>() }

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int) = MyViewHolder(viewGroup)

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.settingName.text = getItem(i).name
        t.settingDescription.text = getItem(i).description
        t.itemView.setOnClickListener { subject.onNext(getItem(i)) }
    }

    fun getItemClickEvent(): Observable<Setting> {
        return subject.throttleFirst(500, TimeUnit.MILLISECONDS).hide()
    }

    inner class MyViewHolder(parent: ViewGroup) : AutoDisposeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
    ) {
        val settingName: TextView = itemView.findViewById(R.id.settingName)
        val settingDescription: TextView = itemView.findViewById(R.id.settingDescription)
    }
}

