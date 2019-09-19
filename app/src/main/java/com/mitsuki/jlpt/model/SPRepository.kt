package com.mitsuki.jlpt.model

import android.content.SharedPreferences
import com.mitsuki.jlpt.BuildConfig
import com.mitsuki.jlpt.app.constants.TTS_KIND
import com.mitsuki.jlpt.app.constants.WORD_VERSION
import com.mitsuki.jlpt.app.int
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.SingletonHolderSingleArg

class SPRepository private constructor(sp: SharedPreferences){

    var ttsKind:Int by sp.int(TTS_KIND, TTSFactory.NATIVE)

    var wordVersion:Int by sp.int(WORD_VERSION, BuildConfig.VERSION_CODE)

    companion object: SingletonHolderSingleArg<SPRepository, SharedPreferences>(::SPRepository)
}