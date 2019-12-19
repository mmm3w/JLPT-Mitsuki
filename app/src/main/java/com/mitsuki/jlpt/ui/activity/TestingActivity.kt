package com.mitsuki.jlpt.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.module.testingKodeinModule
import com.mitsuki.jlpt.ui.adapter.TestingAdapter
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCallback
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCardLayoutManager
import com.mitsuki.jlpt.viewmodel.TTSSettingViewState
import com.mitsuki.jlpt.viewmodel.TestingViewModel
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_testing.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TestingActivity : BaseActivity<TestingViewModel>() {

    override val kodeinModule: Kodein.Module = testingKodeinModule
    override val viewModel: TestingViewModel by instance()

    private val mAdapter: TestingAdapter by instance()
    private val itemTouchCallback: SwipeCallback by lazy { SwipeCallback() }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_testing

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
        initSubscription()
        
        viewModel.initTestData()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "小测试"
    }

    private fun initRecyclerView() {
        testCardList.layoutManager = SwipeCardLayoutManager()
        testCardList.adapter = mAdapter
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(testCardList)
    }

    private fun initSubscription() {
        itemTouchCallback.observable().autoDisposable(scopeProvider).subscribe(this::onSwipeEvent)
        viewModel.getViewModelObservable().autoDisposable(scopeProvider)
            .subscribe(this::onViewStateEvent)
    }


    private fun onViewStateEvent(viewState: TestingViewModel.TestingViewState) {
        viewState.initialData?.apply {
            mAdapter.addAll(this)
            mAdapter.notifyDataSetChanged()
        }

        viewState.word?.apply {
            mAdapter.add(this)
            mAdapter.notifyItemChanged(mAdapter.itemCount - 1)
        }
    }

    private fun onSwipeEvent(state: SwipeCallback.SwipeCallbackState) {
//        mAdapter.remove(state.direction)
//        mAdapter.notifyItemChanged(state.direction)
    }
}