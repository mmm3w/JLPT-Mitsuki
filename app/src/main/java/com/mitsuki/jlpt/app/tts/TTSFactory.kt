package com.mitsuki.jlpt.app.tts

import android.content.Context

class TTSFactory {

    companion object {
        const val NATIVE = 0
        const val GOOGLE_TRANSLATE = 1

        fun create(context: Context, kind: Int): Speaker {
            return when (kind) {
                NATIVE -> NativeTTS.createSpeaker(context)
                GOOGLE_TRANSLATE -> GoogleTranslateTTS.createSpeaker(context)
                else -> NativeTTS.createSpeaker(context)
            }
        }
    }
}
