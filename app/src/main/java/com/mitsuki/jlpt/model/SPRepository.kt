package com.mitsuki.jlpt.model

import android.content.SharedPreferences
import com.mitsuki.jlpt.BuildConfig
import com.mitsuki.jlpt.app.constants.TTS_KIND
import com.mitsuki.jlpt.app.constants.WORD_KIND
import com.mitsuki.jlpt.app.constants.WORD_VERSION
import com.mitsuki.jlpt.app.constants.WORD_VERSION_TEMP
import com.mitsuki.jlpt.app.int
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.SingletonHolderSingleArg

class SPRepository private constructor(sp: SharedPreferences){

    var ttsKind:Int by sp.int(TTS_KIND, TTSFactory.NATIVE)

    var wordVersion:Int by sp.int(WORD_VERSION, BuildConfig.VERSION_CODE)

    var tempWordVersion:Int by sp.int(WORD_VERSION_TEMP, BuildConfig.VERSION_CODE)

    var wordKind:Int by sp.int(WORD_KIND, Integer.MIN_VALUE)

    companion object: SingletonHolderSingleArg<SPRepository, SharedPreferences>(::SPRepository)
}