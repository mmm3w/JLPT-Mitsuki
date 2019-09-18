package com.mitsuki.jlpt.viewmodel

import android.util.Log
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.model.NumeralModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NumeralViewModel(private val model: NumeralModel) : BaseViewModel() {
    private val subject: BehaviorSubject<NumeralViewState> =
        BehaviorSubject.createDefault(NumeralViewState.initial())

    fun getDataObservable(): Observable<NumeralViewState> = subject.hide()

    fun getNumeralSort() {
        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(this).subscribe {
            subject.onNext(subject.value!!.copy(words = null, numeralSort = model.getNumeralSort()))
        }
    }

    fun getNumeralDetail(sort: NumeralSort) {
        Completable.fromAction {}.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
            .autoDisposable(this).subscribe {
                val temp = subject.value!!.copy(
                    words = model.getNumeralDetail(sort.start, sort.total), numeralSort = null
                )
                subject.onNext(temp)
            }
    }
}