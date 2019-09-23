package com.mitsuki.jlpt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.*

class MainViewModelFactory(private val model: MainModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(model) as T
}

class SettingViewModelFactory(private val model: SettingModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SettingViewModel(model) as T
}

class DataCheckViewModelFactory(private val model: DataCheckModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DataCheckViewModel(model) as T
}

class NumeralViewModelFactory(private val model: NumeralModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = NumeralViewModel(model) as T
}
class TTSSettingViewModelFactory(private val model: TTSSettingModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TTSSettingViewModel(model) as T
}


