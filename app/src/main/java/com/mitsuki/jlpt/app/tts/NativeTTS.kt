package com.mitsuki.jlpt.app.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class NativeTTS private constructor(context: Context) : Speaker {

    private var textToSpeech: TextToSpeech
    private var message: String = ""

    init {
        textToSpeech = TextToSpeech(context) {
            message = when (it) {
                TextToSpeech.SUCCESS -> ""
                TextToSpeech.ERROR -> "TTS错误"
                else -> "TTS停止"
            }
        }
    }

    override fun speak(tag: String, text: String, callback: (msg: String) -> Unit) {

        val result = textToSpeech.setLanguage(Locale.JAPANESE)
        if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
            callback.invoke("TTS暂时不支持这种语音的朗读！")
            return
        }

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, tag)
    }

    companion object {
        fun createSpeaker(context: Context): NativeTTS {
            return NativeTTS(context)
        }
    }

}