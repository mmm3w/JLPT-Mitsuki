package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.app.tts.SpeakUtils
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.model.TTSSettingModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class TTSSettingViewModel(private val model: TTSSettingModel) : BaseViewModel() {
    private val eventSubject: PublishSubject<TTSSettingViewState> = PublishSubject.create()

    fun observeEvent(): Observable<TTSSettingViewState> =
        eventSubject.hide().observeOn(AndroidSchedulers.mainThread())

    fun getSetting() {
        eventSubject.onNext(TTSSettingViewState(items = model.getSetting()))
    }

    fun replaceTTS(kind: Int) {
        model.saveTTSKind(kind)
        SpeakUtils.resetSpeaker()
        getSetting()
    }

    fun onTTSSelectEvent() {
        eventSubject.onNext(
            TTSSettingViewState(
                ttsList = model.obtainTTS(),
                ttsSelection = model.obtainTTS().indexOf(TTSFactory.ttsStr(model.obtainTTSKind()))
            )
        )
    }
}