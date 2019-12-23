package com.mitsuki.jlpt.model

import com.mitsuki.jlpt.app.SettingFactory
import com.mitsuki.jlpt.app.kind.KindFactory
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.entity.Setting

class TestingSettingModel(private val spRepository: SPRepository) : BaseModel() {
    fun getSetting(): List<Setting> {
        return arrayListOf(KindFactory.getKind(spRepository.testingKindRange).run {
            SettingFactory.create(type = Setting.SETTING_TESTING_KIND, desc = name, ext = this)
        }, KindFactory.getTestingDisplay(spRepository.testingDisplayRange).run {
            SettingFactory.create(type = Setting.SETTING_TESTING_DISPLAY, desc = name, ext = this)
        }, spRepository.testingJudgeRange.run {
            SettingFactory.create(type = Setting.SETTING_TESTING_JUDGE, ext = this)
        })
    }

    fun saveTestingKindRange(range: Int) {
        spRepository.testingKindRange = range
    }

    fun saveTestingDisplayRange(range: Int) {
        spRepository.testingDisplayRange = range
    }

    fun saveTestingJudgeRange(range: Boolean) {
        spRepository.testingJudgeRange = range
    }

    fun obtainKindList() = KindFactory.kindList

    fun obtainDisplayList() = KindFactory.displayList

    fun obtainSelectedKind() = spRepository.testingKindRange

    fun obtainSelectedDisplay() = spRepository.testingDisplayRange
}