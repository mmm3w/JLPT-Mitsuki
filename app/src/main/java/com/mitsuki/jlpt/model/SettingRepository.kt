package com.mitsuki.jlpt.model

import android.content.SharedPreferences
import com.mitsuki.jlpt.app.constants.TTS_KIND
import com.mitsuki.jlpt.app.int
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.SingletonHolderSingleArg

class SettingRepository private constructor(sp: SharedPreferences){

    var ttsKind:Int by sp.int(TTS_KIND, TTSFactory.NATIVE)

    companion object: SingletonHolderSingleArg<SettingRepository, SharedPreferences>(::SettingRepository)
}