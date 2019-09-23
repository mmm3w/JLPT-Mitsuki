package com.mitsuki.jlpt.app.tts

import android.content.Context
import com.mitsuki.jlpt.app.constants.TTS_KIND
import com.mitsuki.jlpt.app.getInt

object SpeakUtils {
    private var speaker: Speaker? = null

    fun speak(context: Context, tag: String, text: String, callback: (msg: String) -> Unit) {
        if (speaker == null) speaker =
            TTSFactory.create(context, context.getInt(TTS_KIND, TTSFactory.NATIVE))
        speaker?.speak(tag, text, callback)
    }

    fun resetSpeaker() {
        speaker?.release()
        speaker = null
    }
}