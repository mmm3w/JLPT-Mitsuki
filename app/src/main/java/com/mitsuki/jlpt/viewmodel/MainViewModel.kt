package com.mitsuki.jlpt.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.mitsuki.jlpt.app.Kind
import com.mitsuki.jlpt.base.AutoDisposeViewModel
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.model.MainModel
import com.uber.autodispose.autoDisposable
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.BehaviorSubject

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

class MainViewModelFactory(private val model: MainModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(model) as T
}