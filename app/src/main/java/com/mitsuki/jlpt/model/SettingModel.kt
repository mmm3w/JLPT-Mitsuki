package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.entity.Setting

class SettingModel(private val spRepository: SPRepository) : BaseModel() {
        fun getSetting():List<Setting>{
            val ttsSetting = object :Setting("TTS设置", spRepository.ttsKind){
                override fun getExtString(): String {
                    return TTSFactory.ttsStr(ext as Int)
                }
            }
            val updateSetting = Setting("更新词表", "联网更新所有单词表")

            val dataDebugSetting = Setting("数据调试", "测试功能")

            return arrayListOf(updateSetting, ttsSetting, dataDebugSetting)
        }
}