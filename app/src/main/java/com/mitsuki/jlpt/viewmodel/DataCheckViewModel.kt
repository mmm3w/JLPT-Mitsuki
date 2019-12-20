package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.app.kind.GenericKind
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
                subject.apply {
                    onNext(DataCheckViewState(GenericKind.N1, model.getWordNumber(GenericKind.N1)))
                    onNext(DataCheckViewState(GenericKind.N2, model.getWordNumber(GenericKind.N2)))
                    onNext(DataCheckViewState(GenericKind.N3, model.getWordNumber(GenericKind.N3)))
                    onNext(DataCheckViewState(GenericKind.N4, model.getWordNumber(GenericKind.N4)))
                    onNext(DataCheckViewState(GenericKind.N5, model.getWordNumber(GenericKind.N5)))
                    onNext(DataCheckViewState(GenericKind.NUMERAL, model.getWordNumber(GenericKind.NUMERAL)))
                    onNext(DataCheckViewState(GenericKind.INVISIBLE, model.getInvisibleNumber()))
                }
            }
    }
}
