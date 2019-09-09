package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.settingKodeinModule
import com.mitsuki.jlpt.ui.adapter.SettingAdapter
import com.mitsuki.jlpt.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_setting.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("Registered")
class SettingActivity : BaseActivity<SettingViewModel>() {

    override val kodeinModule: Kodein.Module = settingKodeinModule

    override val viewModel: SettingViewModel by instance()
    private val mAdapter: SettingAdapter by instance()


    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_setting

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "设置"
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = mAdapter
        mAdapter.addAll(viewModel.linkSetting())
    }
}