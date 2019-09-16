package com.mitsuki.jlpt.app.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import org.w3c.dom.Text
import java.util.*

class NativeTTS private constructor(context: Context) : Speaker {

    private var textToSpeech: TextToSpeech? = null
    private var message: String = ""

    init {
        textToSpeech = TextToSpeech(context) {
            message = if (it ==  TextToSpeech.SUCCESS) {
                val result = textToSpeech?.setLanguage(Locale.JAPANESE)
                if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                    "TTS暂时不支持这种语音的朗读！"
                }
                ""
            }else{
                "TTS错误"
            }
        }
    }

    override fun speak(tag: String, text: String, callback: (msg: String) -> Unit) {
        if (textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, tag) != TextToSpeech.SUCCESS){
            if (!TextUtils.isEmpty(message)) callback.invoke(message)
        }
    }

    companion object {
        fun createSpeaker(context: Context): NativeTTS {
            return NativeTTS(context)
        }
    }

}