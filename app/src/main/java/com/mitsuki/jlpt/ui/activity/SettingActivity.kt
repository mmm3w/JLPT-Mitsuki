package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.settingKodeinModule
import com.mitsuki.jlpt.viewmodel.SettingViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("Registered")
class SettingActivity:BaseActivity<SettingViewModel>() {
    override val kodeinModule: Kodein.Module = settingKodeinModule
    override val viewModel: SettingViewModel by instance()



    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_setting

    override fun initData(savedInstanceState: Bundle?) {

    }
}