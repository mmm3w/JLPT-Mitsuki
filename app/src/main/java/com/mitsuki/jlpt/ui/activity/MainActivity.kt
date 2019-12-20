package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.hint.showOperationResult
import com.mitsuki.jlpt.app.hint.toastShort
import com.mitsuki.jlpt.app.kind.GenericKind
import com.mitsuki.jlpt.app.tts.SpeakUtils
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.mainKodeinModule
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.ui.widget.MainToolbarController
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import com.mitsuki.jlpt.ui.widget.smoothscroll.SmoothScrollLayoutManager
import com.mitsuki.jlpt.viewmodel.MainEvent
import com.mitsuki.jlpt.viewmodel.MainViewModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<MainViewModel>() {

    override val kodeinModule = mainKodeinModule
    override val viewModel: MainViewModel by instance()

    private val mAdapter: WordAdapter by instance()
    private val swipeDeleteEvent: SwipeDeleteEvent by instance()

    private val toolbarController by lazy { MainToolbarController(toolbar) }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()
        initSubscription()

        viewModel.switchMode(0, isInitial = true)
        viewModel.checkWordVersion()
    }

    /**********************************************************************************************/
    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        ItemTouchHelper(swipeDeleteEvent).attachToRecyclerView(wordList)
        wordList.layoutManager = SmoothScrollLayoutManager(this)
        wordList.adapter = mAdapter
        wordList.addOnScrollListener(toolbarController)
    }

    private fun initSubscription() {
        //滑动删除
        swipeDeleteEvent.onSwipe.observeOn(Schedulers.io()).autoDisposable(scopeProvider)
            .subscribe { viewModel.changeWordState(it, mAdapter.getItemForOut(it)) }
        //tts
        mAdapter.parentSubject.autoDisposable(scopeProvider)
            .subscribe { SpeakUtils.speak(this, it.cn, it.kana) { toastShort { it } } }
        //数据加载
        viewModel.observeData().autoDisposable(scopeProvider)
            .subscribe { mAdapter.submitList(it) { viewModel.checkListStatus() } }
        //viewModel其他事件
        viewModel.observeEvent().observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider).subscribe(this::onViewModelEvent)
    }

    /**********************************************************************************************/
    @Suppress("NON_EXHAUSTIVE_WHEN")
    private fun onViewModelEvent(event: MainViewModel.ViewState) {
        event.kind?.apply {
            title = name
            mAdapter.setListMode(type != GenericKind.INVISIBLE)
        }

        event.normalEvent?.apply {
            when (this) {
                //MainEvent.SCROLL_TO_TOP -> wordList.smoothScrollToPosition(0) //TODO:有bug，暂时禁用
                MainEvent.SHOW_SNACKBAR -> showSnackbar()
                MainEvent.NEW_WORD_VERSION -> toastShort { "有新版本单词" }
            }
        }
    }

    private fun showSnackbar() = wordList.showOperationResult("操作成功", "撤销") {
        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(scopeProvider)
            .subscribe { viewModel.undoOperation() }
    }

    /**********************************************************************************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.nav_all -> viewModel.switchMode(GenericKind.ALL)
            R.id.nav_n1 -> viewModel.switchMode(GenericKind.N1)
            R.id.nav_n2 -> viewModel.switchMode(GenericKind.N2)
            R.id.nav_n3 -> viewModel.switchMode(GenericKind.N3)
            R.id.nav_n4 -> viewModel.switchMode(GenericKind.N4)
            R.id.nav_n5 -> viewModel.switchMode(GenericKind.N5)
            R.id.nav_invisible -> viewModel.switchMode(GenericKind.INVISIBLE)
            R.id.nav_numeral -> startActivity(Intent(this, NumeralActivity::class.java))
            R.id.nav_test -> startActivity(Intent(this, TestingActivity::class.java))
            R.id.nav_setting -> startActivity(Intent(this, SettingActivity::class.java))
        }
        return false
    }
}
