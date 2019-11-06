package com.mitsuki.jlpt.ui.activity

import android.os.Bundle
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.testingKodeinModule
import com.mitsuki.jlpt.viewmodel.TestingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TestingActivity : BaseActivity<TestingViewModel>() {

    override val kodeinModule: Kodein.Module = testingKodeinModule
    override val viewModel: TestingViewModel by instance()

    override fun initView(savedInstanceState: Bundle?): Int  = R.layout.activity_testing

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "小测试"
    }
}