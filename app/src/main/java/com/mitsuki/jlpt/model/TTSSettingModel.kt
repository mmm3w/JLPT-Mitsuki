package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.entity.Setting

class TTSSettingModel(private val spRepository: SPRepository) : BaseModel() {
    fun getSetting(): List<Setting> {
        return arrayListOf(
            Setting.create(
                type = Setting.SETTING_TTS_SELECT,
                desc = TTSFactory.ttsStr(spRepository.ttsKind),
                ext = spRepository.ttsKind
            ),
            Setting.create(type = Setting.SETTING_TTS),
            Setting.create(type = Setting.SETTING_TTS_TESTING)
        )
    }

    fun saveTTSKind(kind: Int) {
        spRepository.ttsKind = kind
    }

    fun obtainTTSKind() = spRepository.ttsKind

    fun obtainTTS() = TTSFactory.list()
}