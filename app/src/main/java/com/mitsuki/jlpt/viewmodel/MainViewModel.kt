package com.mitsuki.jlpt.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.model.MainModel
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor

@SuppressLint("CheckResult")
class MainViewModel(private val model: MainModel) : ViewModel() {

    private val dataProcessor: BehaviorProcessor<PagedList<Word>> = BehaviorProcessor.create()
    private var undoCache: WordState? = null
    private var disposable: Disposable? = null

    fun switchMode(mode: Int) {
        disposable?.dispose()
        disposable = when (mode) {
            0 -> model.fetchAllWord().subscribe { dataProcessor.onNext(it) }
            1 -> model.fetchWordWithN1().subscribe { dataProcessor.onNext(it) }
            2 -> model.fetchWordWithN2().subscribe { dataProcessor.onNext(it) }
            3 -> model.fetchWordWithN3().subscribe { dataProcessor.onNext(it) }
            4 -> model.fetchWordWithN4().subscribe { dataProcessor.onNext(it) }
            5 -> model.fetchWordWithN5().subscribe { dataProcessor.onNext(it) }
            -1 -> model.fetchWordWithInvisible().subscribe { dataProcessor.onNext(it) }
            else -> null
        }
    }

    fun observeData(): Flowable<PagedList<Word>> = dataProcessor.hide()

    fun hideWord(word: Word?) {
        word?.also {
            val s = WordState(it.id, fav = false, visible = false)
            model.modifyWordState(s)
            undoCache = s
            undoCache?.visible = true
        }

    }

    fun undoOperation() {
        undoCache?.also { model.modifyWordState(it) }
    }
}

class MainViewModelFactory(private val model: MainModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(model) as T
}