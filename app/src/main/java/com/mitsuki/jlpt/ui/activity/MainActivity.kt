package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.instance
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.mainKodeinModule
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity<MainViewModel>() {

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
        viewModel.switchMode(-1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    @SuppressLint("CheckResult")
    private fun initRecyclerView() {
        itemTouchHelper.attachToRecyclerView(wordList)
        wordList.layoutManager = LinearLayoutManager(this)
        wordList.adapter = mAdapter

        viewModel.observeData()
            .autoDisposable(scopeProvider)
            .subscribe {
                mAdapter.submitList(it)
                showSnackbar()
            }

        swipeDeleteEvent.onSwipe.observeOn(Schedulers.io())
            .autoDisposable(scopeProvider)
            .subscribe {
                snackBol = true
                viewModel.changeWordState(mAdapter.getItemForOut(it))
            }
    }

    private fun showSnackbar() {
        if (!snackBol) return
        snackBol = false
        Snackbar.make(wordList, "操作成功", Snackbar.LENGTH_LONG)
            .setAction("撤销") {
                Completable.fromAction {}
                    .observeOn(Schedulers.io())
                    .autoDisposable(scopeProvider)
                    .subscribe { viewModel.undoOperation() }
            }
            .show()
    }
}
