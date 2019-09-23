package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import com.mitsuki.jlpt.model.MainModel
import com.mitsuki.jlpt.model.TTSSettingModel
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.ui.widget.SwipeDeleteEvent
import com.mitsuki.jlpt.viewmodel.MainViewModel
import com.mitsuki.jlpt.viewmodel.MainViewModelFactory
import com.mitsuki.jlpt.viewmodel.TTSSettingViewModel
import com.mitsuki.jlpt.viewmodel.TTSSettingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val TTS_SETTING_MODULE_TAG = "TTS_SETTING_MODULE_TAG"

val ttsSettingKodeinModule = Kodein.Module(TTS_SETTING_MODULE_TAG) {
    //Model
    bind<TTSSettingModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        TTSSettingModel(spRepository = instance())
    }
    //ViewModel
    bind<TTSSettingViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, TTSSettingViewModelFactory(model = instance())
        ).get(TTSSettingViewModel::class.java)
    }

}