package com.mitsuki.jlpt.app

import com.mitsuki.jlpt.entity.Setting

object SettingFactory {
    fun create(type: Int, desc: String = "", ext: Any? = null): Setting {
        return when (type) {
            Setting.SETTING_TTS_ALL -> Setting(
                type = type, name = "TTS设置", description = "TTS方式选择、TTS详细设置、TTS发声示例"
            )
            Setting.SETTING_UPDATE_WORD -> Setting(
                type = type, name = "更新词表", description = "联网更新所有单词表"
            )
            Setting.SETTING_DATA_DEBUG -> Setting(type = type, name = "数据调试", description = "测试功能")
            Setting.SETTING_TTS_SELECT -> Setting(
                type = type, name = "TTS选择", description = desc, ext = ext
            )
            Setting.SETTING_TTS -> Setting(type = type, name = "TTS设置", description = "前往系统TTS设置界面")
            Setting.SETTING_TTS_TESTING -> Setting(
                type = type, name = "TTS测试", description = "播放TTS示例"
            )
            Setting.SETTING_TESTING_KIND -> Setting(
                type = type, name = "测试词库", description = desc, ext = ext
            )
            Setting.SETTING_TESTING_DISPLAY -> Setting(
                type = type, name = "是否包含隐藏单词", description = desc, ext = ext
            )
            Setting.SETTING_TESTING_JUDGE -> Setting(
                type = type, name = "答案仅限假名", ext = ext
            )
            else -> Setting()
        }
    }

    val topSettingList by lazy {
        arrayListOf(
            create(Setting.SETTING_UPDATE_WORD),
            create(Setting.SETTING_TTS_ALL),
            create(Setting.SETTING_DATA_DEBUG)
        )
    }
}