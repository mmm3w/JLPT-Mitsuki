package com.mitsuki.jlpt.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.model.MainModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor

@SuppressLint("CheckResult")
class MainViewModel(private val model: MainModel) : BaseViewModel() {
    private val dataProcessor: BehaviorProcessor<PagedList<Word>> = BehaviorProcessor.create()
    private var undoCache: WordState? = null
    private var disposable: Disposable? = null
    private var mode = 0

    fun switchMode(mode: Int) {
        this.mode = mode
        disposable?.dispose()
        disposable = when (mode) {
            Kind.ALL -> model.fetchAllWord().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N1 -> model.fetchWordWithN1().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N2 -> model.fetchWordWithN2().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N3 -> model.fetchWordWithN3().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N4 -> model.fetchWordWithN4().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N5 -> model.fetchWordWithN5().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.INVISIBLE -> model.fetchWordWithInvisible().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            else -> null
        }
    }

    fun observeData(): Flowable<PagedList<Word>> = dataProcessor.hide()

    fun changeWordState(word: Word?) {
        word?.also {
            val s = WordState(it.id, fav = false, visible = mode == Kind.INVISIBLE)
            model.modifyWordState(s)
            undoCache = s
            undoCache?.visible = !s.visible
        }
    }

    fun undoOperation() {
        undoCache?.also { model.modifyWordState(it) }
    }

}

