package com.mitsuki.jlpt.app.tts

import android.content.Context

object TTSFactory {
    const val NATIVE = 0
    const val GOOGLE_TRANSLATE = 1

    const val NATIVE_STR = "Native TTS"
    const val GOOGLE_TRANSLATE_STR = "Google Translate TTS"

    fun create(context: Context, kind: Int): Speaker {
        return when (kind) {
            NATIVE -> NativeTTS.createSpeaker(context)
            GOOGLE_TRANSLATE -> GoogleTranslateTTS.createSpeaker(context)
            else -> NativeTTS.createSpeaker(context)
        }
    }

    fun list() = arrayListOf(NATIVE_STR, GOOGLE_TRANSLATE_STR)

    fun ttsStr(tts: Int): String {
        return when (tts) {
            NATIVE -> NATIVE_STR
            GOOGLE_TRANSLATE -> GOOGLE_TRANSLATE_STR
            else -> ""
        }
    }

    fun ttsInt(tts: String): Int {
        return when (tts) {
            NATIVE_STR -> NATIVE
            GOOGLE_TRANSLATE_STR -> GOOGLE_TRANSLATE
            else -> -1
        }
    }
}
