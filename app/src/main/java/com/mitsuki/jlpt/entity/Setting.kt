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

        const val SETTING_TESTING_KIND = 0x0401
        const val SETTING_TESTING_DISPLAY = 0x0402
    }
}