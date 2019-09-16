package com.mitsuki.jlpt.ui.activity

import android.os.Bundle
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.numeralKodeinModule
import com.mitsuki.jlpt.viewmodel.NumeralViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class NumeralActivity : BaseActivity<NumeralViewModel>() {
    override val kodeinModule: Kodein.Module = numeralKodeinModule
    override val viewModel: NumeralViewModel by instance()

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_numeral

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "数词/量词"
    }

}