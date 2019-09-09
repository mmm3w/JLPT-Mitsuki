package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Setting

class SettingAdapter : BaseAdapter<Setting, SettingAdapter.MyViewHolder>() {

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int) = MyViewHolder(viewGroup)

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.settingName.text = getItem(i).text
        t.settingDescription.text = getItem(i).getExtString()
    }

    inner class MyViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)) {
        val settingName: TextView = itemView.findViewById(R.id.settingName)
        val settingDescription: TextView = itemView.findViewById(R.id.settingDescription)
        val settingExt :FrameLayout = itemView.findViewById(R.id.settingExt)
    }
}

