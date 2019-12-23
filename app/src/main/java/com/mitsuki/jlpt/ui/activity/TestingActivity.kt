package com.mitsuki.jlpt.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.kind.KindFactory
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.module.testingKodeinModule
import com.mitsuki.jlpt.ui.adapter.TestingAdapter
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCallback
import com.mitsuki.jlpt.ui.widget.swipecard.SwipeCardLayoutManager
import com.mitsuki.jlpt.viewmodel.TTSSettingViewState
import com.mitsuki.jlpt.viewmodel.TestingViewModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_testing.*
import kotlinx.android.synthetic.main.item_word_entry.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TestingActivity : BaseActivity<TestingViewModel>() {

    override val kodeinModule: Kodein.Module = testingKodeinModule
    override val viewModel: TestingViewModel by instance()

    private val mAdapter: TestingAdapter by instance()

    private val itemTouchCallback: SwipeCallback by lazy { SwipeCallback() }
    private val dialog by lazy { MaterialDialog(this) }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_testing

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()
        initSubscription()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initTestData()
    }

    /**********************************************************************************************/
    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "单词测试"
    }

    private fun initComponent() {
        testCardList.layoutManager = SwipeCardLayoutManager()
        testCardList.adapter = mAdapter
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(testCardList)

        mAdapter.confirmEvent = ::onConfirmDialog
    }

    private fun initSubscription() {
        itemTouchCallback.observable().autoDisposable(scopeProvider).subscribe(this::onSwipeEvent)
        viewModel.getViewModelObservable().autoDisposable(scopeProvider)
            .subscribe(this::onViewStateEvent)
    }

    /**********************************************************************************************/
    private fun onViewStateEvent(viewState: TestingViewModel.TestingViewState) {
        viewState.initialData?.apply {
            mAdapter.judgeTag = viewState.judgeTag
            mAdapter.addAll(this)
            mAdapter.notifyDataSetChanged()
        }

        viewState.word?.apply {
            mAdapter.remove(0)
            if (id != -1) {
                mAdapter.add(this)
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    /**********************************************************************************************/
    private fun onSwipeEvent(state: SwipeCallback.SwipeCallbackState) {
        //移除最顶部的，然后更新
        viewModel.updateTestData()
    }

    private fun onConfirmDialog(callback: () -> WordState) {
        dialog.show {
            message(text = "确认提交？")
            positiveButton(text = "确认") { viewModel.cachingLastTestingResult(callback.invoke()) }
            negativeButton(text = "取消") { it.dismiss() }
        }
    }

    /**********************************************************************************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_testing, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.testing_setting -> startActivity(Intent(this, TestingSettingActivity::class.java))
        }
        return false
    }
}