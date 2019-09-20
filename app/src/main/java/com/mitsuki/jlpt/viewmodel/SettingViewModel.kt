package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.model.SettingModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SettingViewModel(private val model: SettingModel) : BaseViewModel() {

    private val subject: PublishSubject<SettingViewState> = PublishSubject.create()

    fun observeEvent(): Observable<SettingViewState> = subject.hide().observeOn(AndroidSchedulers.mainThread())

    fun linkSetting() = model.getSetting()

    fun updateWords() {
        subject.onNext(SettingViewState(SettingState.REQUEST_VERSION))
        model.requestVersion().observeOn(Schedulers.io())
            .autoDisposable(this).subscribe {
            if (it == -1) {
                //无新版本
                subject.onNext(SettingViewState(SettingState.NO_NEW_VERSION))
            } else {
                //有新版本
                subject.onNext(SettingViewState(SettingState.HAVE_NEW_VERSION))
                requestData()
            }
        }
    }

    private fun requestData() {
        model.requestData().observeOn(Schedulers.io())
            .autoDisposable(this).subscribe {
            when (it.what) {
                -1 -> subject.onNext(SettingViewState(SettingState.DOWNLOAD_DATA_ERROR, it.obj.toString()))
                0 -> subject.onNext(SettingViewState(SettingState.DOWNLOAD_DATA_SUCCESS))
                1 -> subject.onNext(SettingViewState(SettingState.UPDATE_DATABASE_SUCCESS))
            }
        }
    }
}