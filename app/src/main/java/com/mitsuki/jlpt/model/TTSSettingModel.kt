package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.entity.Setting

class TTSSettingModel(private val spRepository: SPRepository) : BaseModel() {
    fun getSetting(): List<Setting> {
        val ttsSelect = object : Setting("TTS选择", spRepository.ttsKind) {
            override fun getExtString(): String {
                return TTSFactory.ttsStr(spRepository.ttsKind)
            }
        }
        val ttsSetting = Setting("TTS设置", "前往系统TTS设置界面")
        val ttsTest = Setting("TTS测试", "播放TTS示例")
        return arrayListOf(ttsSelect, ttsSetting, ttsTest)
    }

    fun saveTTSKind(kind:Int){
        spRepository.ttsKind = kind
    }
}