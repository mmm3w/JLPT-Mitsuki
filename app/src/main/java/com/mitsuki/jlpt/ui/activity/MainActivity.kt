package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.app.getInt
import com.mitsuki.jlpt.app.getKind
import com.mitsuki.jlpt.app.putInt
import com.mitsuki.jlpt.app.showOperationResult
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.mainKodeinModule
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<MainViewModel>() {

    private val WORD_KIND = "WORD_KIND"

    override val kodeinModule = mainKodeinModule
    override val viewModel: MainViewModel by instance()

    private val mAdapter: WordAdapter by instance()
    private val itemTouchHelper: ItemTouchHelper by instance()
    private val swipeDeleteEvent: SwipeDeleteEvent by instance()
    private var snackBol = false

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_main
    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()

        switchMode(getInt(WORD_KIND))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.nav_all -> switchMode(0)
            R.id.nav_n1 -> switchMode(1)
            R.id.nav_n2 -> switchMode(2)
            R.id.nav_n3 -> switchMode(3)
            R.id.nav_n4 -> switchMode(4)
            R.id.nav_n5 -> switchMode(5)
            R.id.nav_numeral -> switchMode(6)
            R.id.nav_invisible -> switchMode(7)
            R.id.nav_test -> switchMode(-2)
        }
        return false
    }

    private fun switchMode(order: Int) {
        getKind(order)?.let {
            viewModel.switchMode(it.getMode())
            if (it.getMode() >= 0) {
                putInt(WORD_KIND, it.getMode())
                title = it.getTitle()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    @SuppressLint("CheckResult")
    private fun initRecyclerView() {
        itemTouchHelper.attachToRecyclerView(wordList)
        wordList.layoutManager = LinearLayoutManager(this)
        wordList.adapter = mAdapter

        viewModel.observeData().autoDisposable(scopeProvider).subscribe {
            mAdapter.submitList(it)
            showSnackbar()
        }

        swipeDeleteEvent.onSwipe.observeOn(Schedulers.io()).autoDisposable(scopeProvider)
            .subscribe {
                snackBol = true
                viewModel.changeWordState(mAdapter.getItemForOut(it))
            }
    }

    private fun showSnackbar() {
        if (!snackBol) return
        snackBol = false
        wordList.showOperationResult("操作成功", "撤销") {
            Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(scopeProvider)
                .subscribe { viewModel.undoOperation() }
        }
    }
}
