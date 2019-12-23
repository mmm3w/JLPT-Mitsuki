package com.mitsuki.jlpt.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.mitsuki.jlpt.entity.Setting

class TestingSettingAdapter : DefaultSettingAdapter() {

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return ViewHolder(viewGroup)
    }

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        super.onMyBindViewHolder(t, i)
        getItem(i).apply {
            if (type == Setting.SETTING_TESTING_JUDGE) {
                (t as ViewHolder).switchView.visibility = View.VISIBLE
                ext?.apply { t.switchView.isChecked = this as Boolean }
            }else{
                (t as ViewHolder).switchView.visibility = View.GONE
            }
        }
    }

    inner class ViewHolder(parent: ViewGroup) : DefaultSettingAdapter.MyViewHolder(parent) {
        val switchView: Switch = Switch(parent.context)

        init {
            switchView.isClickable = false
            settingExtend.addView(switchView)
        }
    }
}