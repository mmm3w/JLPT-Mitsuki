package com.mitsuki.jlpt.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.entity.Setting

class SettingAdapter : PagedListAdapter<Setting, SettingAdapter.MyViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Setting>() {
            override fun areItemsTheSame(oldItem: Setting, newItem: Setting): Boolean =
                oldItem.text == newItem.text

            override fun areContentsTheSame(oldItem: Setting, newItem: Setting): Boolean =
                oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.settingName.text = it.text
            holder.settingDescription.text = it.getExtString()
            holder.itemView.setOnClickListener { _ -> it.callback.invoke(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    inner class MyViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)) {
        val settingName: TextView = itemView.findViewById(R.id.settingName)
        val settingDescription: TextView = itemView.findViewById(R.id.settingDescription)
        val settingExt :FrameLayout = itemView.findViewById(R.id.settingExt)
    }
}

