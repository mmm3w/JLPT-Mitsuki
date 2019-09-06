package com.mitsuki.jlpt.app.tts

interface Speaker {
    fun speak(tag: String, text: String, callback: (msg:String) -> Unit)
}