package com.mitsuki.jlpt.model

import android.content.SharedPreferences
import com.mitsuki.jlpt.BuildConfig
import com.mitsuki.jlpt.app.constants.*
import com.mitsuki.jlpt.app.int
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.SingletonHolderSingleArg

class SPRepository private constructor(sp: SharedPreferences){

    var ttsKind:Int by sp.int(TTS_KIND, TTSFactory.NATIVE)

    var wordVersion:Int by sp.int(WORD_VERSION, BuildConfig.VERSION_CODE)

    var tempWordVersion:Int by sp.int(WORD_VERSION_TEMP, BuildConfig.VERSION_CODE)

    var wordKind:Int by sp.int(WORD_KIND, Integer.MIN_VALUE)

//    var testingKindRange:Int by sp.int(TESTING_KIND_RANGE ,)
//    var testingDisplayRange:Int by sp.int(TESTING_DISPLAY_RANGE ,)

    companion object: SingletonHolderSingleArg<SPRepository, SharedPreferences>(::SPRepository)
}