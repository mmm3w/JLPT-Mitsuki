package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.MainViewModel
import com.mitsuki.jlpt.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import io.reactivex.schedulers.Schedulers
import com.google.android.material.snackbar.Snackbar
import com.mitsuki.jlpt.app.TempUtils
import io.reactivex.Observable


class MainActivity : AppCompatActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
        bind<MainViewModel>() with singleton {
            ViewModelProviders.of(this@MainActivity, MainViewModelFactory(db = instance()))
                    .get(MainViewModel::class.java)
        }
        bind<WordAdapter>() with singleton { WordAdapter() }
    }

    private val viewModel: MainViewModel by instance()
    private val mAdapter: WordAdapter by instance()
    private var snackBol = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    @SuppressLint("CheckResult")
    private fun initRecyclerView() {
        val callback = SwipeDeleteEvent()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(wordList)
        wordList.layoutManager = LinearLayoutManager(this)
        wordList.adapter = mAdapter
        viewModel.allWord.observe(this, Observer {
            showSnackbar()
            mAdapter.submitList(it)
        })
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
                .setAction("撤销") { Observable.just("").observeOn(Schedulers.io()).subscribe { viewModel.undoOperation() } }
                .show()
    }
}
