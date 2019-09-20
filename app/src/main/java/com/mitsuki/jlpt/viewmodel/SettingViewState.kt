package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Word

data class SettingViewState(val state: SettingState, val msg: String = "")

enum class SettingState {
    REQUEST_VERSION, HAVE_NEW_VERSION, NO_NEW_VERSION, DOWNLOAD_DATA_ERROR, DOWNLOAD_DATA_SUCCESS, UPDATE_DATABASE_SUCCESS
}