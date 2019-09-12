package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.model.DataCheckModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DataCheckViewModel(private val model: DataCheckModel) : BaseViewModel() {

    private val subject: PublishSubject<DataCheckViewState> = PublishSubject.create()

    fun getDataObservable(): Observable<DataCheckViewState> = subject.hide()

    fun checkDataNumber() {
        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(this)
            .subscribe {
                subject.onNext(DataCheckViewState(Kind.N1, model.getWordNumber(Kind.N1)))
                subject.onNext(DataCheckViewState(Kind.N2, model.getWordNumber(Kind.N2)))
                subject.onNext(DataCheckViewState(Kind.N3, model.getWordNumber(Kind.N3)))
                subject.onNext(DataCheckViewState(Kind.N4, model.getWordNumber(Kind.N4)))
                subject.onNext(DataCheckViewState(Kind.N5, model.getWordNumber(Kind.N5)))
                subject.onNext(DataCheckViewState(Kind.NUMERAL, model.getNumeralNumber()))
                subject.onNext(DataCheckViewState(Kind.INVISIBLE, model.getInvisibleNumber()))
            }
    }
}
