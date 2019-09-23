package com.mitsuki.jlpt.app.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class GoogleTranslateTTS private constructor(context: Context) : Speaker {

    init {

    }

    override fun speak(tag: String, text: String, callback: (msg: String) -> Unit) {
        callback.invoke("暂不支持此功能")
    }

    override fun release() {
    }

    companion object {
        fun createSpeaker(context: Context): GoogleTranslateTTS {
            return GoogleTranslateTTS(context)
        }
    }
}