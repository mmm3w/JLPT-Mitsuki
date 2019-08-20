package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.mitsuki.jlpt.module.mainKodeinModule
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
        import(mainKodeinModule)
    }

    private val viewModel: MainViewModel by instance()
    private val mAdapter: WordAdapter by instance()
    private var snackBol = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initRecyclerView()

        viewModel.switchMode(-1)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //隐藏滚动条
        navigationView.getChildAt(0)?.let { it.isVerticalScrollBarEnabled = false }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_all -> viewModel.switchMode(0)
                R.id.nav_n1 -> viewModel.switchMode(1)
                R.id.nav_n2 -> viewModel.switchMode(2)
                R.id.nav_n3 -> viewModel.switchMode(3)
                R.id.nav_n4 -> viewModel.switchMode(4)
                R.id.nav_n5 -> viewModel.switchMode(5)
                R.id.nav_numeral -> viewModel.switchMode(6)
                R.id.nav_invisible -> viewModel.switchMode(-1)
            }
            drawerLayout.closeDrawers()
            if (it.itemId != R.id.nav_test) {
                it.isChecked = true
                title = it.title
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun initRecyclerView() {
        val callback = SwipeDeleteEvent()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(wordList)
        wordList.layoutManager = LinearLayoutManager(this)
        wordList.adapter = mAdapter

        viewModel.observeData().subscribe {
            mAdapter.submitList(it)
            showSnackbar()
        }

        callback.onSwipe.observeOn(Schedulers.io())
                .subscribe {
                    snackBol = true
                    viewModel.hideWord(mAdapter.getItemForOut(it))
                }
    }

    private fun showSnackbar() {
        if (!snackBol) return
        snackBol = false
        Snackbar.make(wordList, "隐藏成功", Snackbar.LENGTH_LONG)
                .setAction("撤销") {
                    Completable.fromAction {}.observeOn(Schedulers.io()).subscribe { viewModel.undoOperation() }
                }
                .show()
    }
}
