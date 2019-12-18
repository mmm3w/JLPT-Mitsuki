package com.mitsuki.jlpt.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.testingKodeinModule
import com.mitsuki.jlpt.ui.adapter.TestingAdapter
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCallback
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCardLayoutManager
import com.mitsuki.jlpt.viewmodel.TestingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_testing.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TestingActivity : BaseActivity<TestingViewModel>() {

    override val kodeinModule: Kodein.Module = testingKodeinModule
    override val viewModel: TestingViewModel by instance()

    private val itemTouchCallback: SwipeCallback by lazy { SwipeCallback() }

    private val mAdapter = TestingAdapter()

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_testing

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "小测试"
    }

    private fun initRecyclerView() {
        testCardList.layoutManager = SwipeCardLayoutManager(this)
        testCardList.adapter = mAdapter
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(testCardList)
    }
}