package com.mitsuki.jlpt.entity

open class Setting(
    val type: Int = SETTING_NULL,
    val name: String = "",
    var description: String = "",
    var ext: Any? = null
) {

    companion object {
        const val SETTING_NULL = -0x0001

        const val SETTING_TTS_ALL = 0x0100
        const val SETTING_UPDATE_WORD = 0x0200
        const val SETTING_DATA_DEBUG = 0x0300

        const val SETTING_TTS_SELECT = 0x0101
        const val SETTING_TTS = 0x0102
        const val SETTING_TTS_TESTING = 0x0103

        fun create(type: Int, desc: String = "", ext: Any? = null): Setting {
            return when (type) {
                SETTING_TTS_ALL -> Setting(
                    type = type, name = "TTS设置", description = "TTS方式选择、TTS设置、TTS发声示例"
                )
                SETTING_UPDATE_WORD -> Setting(
                    type = type, name = "更新词表", description = "联网更新所有单词表"
                )
                SETTING_DATA_DEBUG -> Setting(type = type, name = "数据调试", description = "测试功能")
                SETTING_TTS_SELECT -> Setting(
                    type = type, name = "TTS选择", description = desc, ext = ext
                )
                SETTING_TTS -> Setting(type = type, name = "TTS设置", description = "前往系统TTS设置界面")
                SETTING_TTS_TESTING -> Setting(type = type, name = "TTS测试", description = "播放TTS示例")
                else -> Setting()
            }
        }

        fun createGroup(): List<Setting> {
            return arrayListOf(
                create(SETTING_UPDATE_WORD), create(SETTING_TTS_ALL), create(SETTING_DATA_DEBUG)
            )
        }
    }
}