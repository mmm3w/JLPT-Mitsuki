package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.app.kind.KindFactory
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.model.TestingSettingModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class TestingSettingViewModel(private val model: TestingSettingModel) : BaseViewModel() {

    private val eventSubject: PublishSubject<TestingSettingViewState> = PublishSubject.create()

    fun viewModelObservable(): Observable<TestingSettingViewState> =
        eventSubject.hide().observeOn(AndroidSchedulers.mainThread())

    fun getSetting() {
        eventSubject.onNext(TestingSettingViewState(list = model.getSetting()))
    }

    fun onDisplaySelectEvent() {
        eventSubject.onNext(model.obtainDisplayList().run {
            TestingSettingViewState(
                displayList = this,
                selection = this.indexOf(KindFactory.type2Name(model.obtainSelectedDisplay())),
                selectType = Setting.SETTING_TESTING_DISPLAY
            )
        }

        )

    }

    fun onKindSelectEvent() {
        eventSubject.onNext(model.obtainKindList().run {
            TestingSettingViewState(
                displayList = this,
                selection = this.indexOf(KindFactory.type2Name(model.obtainSelectedKind())),
                selectType = Setting.SETTING_TESTING_KIND
            )
        })
    }

    fun onSelectEvent(type: Int, str: String) {
        when (type) {
            Setting.SETTING_TESTING_KIND -> model.saveTestingKindRange(
                KindFactory.name2Type(str)
            )
            Setting.SETTING_TESTING_DISPLAY -> model.saveTestingDisplayRange(
                KindFactory.name2Type(str)
            )
        }
        getSetting()
    }


    data class TestingSettingViewState(
        val list: List<Setting>? = null,
        val displayList: List<String>? = null,
        val kindList: List<String>? = null,
        val selection: Int = 0,
        val selectType: Int? = null
    )
}