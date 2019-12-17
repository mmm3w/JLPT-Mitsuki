package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.entity.Setting

data class TTSSettingViewState(
    val items: List<Setting>? = null,
    val ttsList: ArrayList<String>? = null,
    val ttsSelection: Int = 0
)