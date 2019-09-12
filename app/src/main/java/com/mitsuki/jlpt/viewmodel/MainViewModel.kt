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
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
class MainViewModel(private val model: MainModel) : BaseViewModel() {
    private val dataProcessor: BehaviorProcessor<PagedList<Word>> = BehaviorProcessor.create()
    private val eventSubject:PublishSubject<MainEvent> = PublishSubject.create()
    private var undoCache: WordState? = null
    private var disposable: Disposable? = null
    private var mode = 0

    private var snackBol = false
    private var lastModify = 0

    fun observeData(): Flowable<PagedList<Word>> = dataProcessor.hide()

    fun observeEvent(): Observable<MainEvent> = eventSubject.hide()

    fun switchMode(mode: Int) {
        this.lastModify = -1
        this.mode = mode
        disposable?.dispose()
        disposable = when (mode) {
            Kind.ALL -> model.fetchAllWord().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N1 -> model.fetchWord(mode).autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N2 -> model.fetchWord(mode).autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N3 -> model.fetchWord(mode).autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N4 -> model.fetchWord(mode).autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.N5 -> model.fetchWord(mode).autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            Kind.INVISIBLE -> model.fetchWordWithInvisible().autoDisposable(this).subscribe { dataProcessor.onNext(it) }
            else -> null
        }
    }

    fun changeWordState(position:Int,word: Word?) {
        snackBol = true
        lastModify = position
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

    fun checkListStatus(){
        if (snackBol){
            snackBol = false
            eventSubject.onNext(MainEvent.SHOW_SNACKBAR)
        }else{
            eventSubject.onNext(MainEvent.EXPAND_APP_BAR)
            if (lastModify == 0) eventSubject.onNext(MainEvent.SCROLL_TO_TOP)
        }
    }
}

enum class MainEvent{
    SHOW_SNACKBAR, SCROLL_TO_TOP, EXPAND_APP_BAR
}

