package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Setting
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SettingAdapter : BaseAdapter<Setting, SettingAdapter.MyViewHolder>() {

    private val subject: PublishSubject<Setting> by lazy { PublishSubject.create<Setting>() }

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int) = MyViewHolder(viewGroup)

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.settingName.text = getItem(i).text
        t.settingDescription.text = getItem(i).getExtString()
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
        val settingExt: FrameLayout = itemView.findViewById(R.id.settingExt)
    }
}

