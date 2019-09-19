package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.SettingModel
import com.mitsuki.jlpt.model.SPRepository
import com.mitsuki.jlpt.ui.adapter.SettingAdapter
import com.mitsuki.jlpt.viewmodel.SettingViewModel
import com.mitsuki.jlpt.viewmodel.SettingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val SETTING_MODULE_TAG = "SETTING_MODULE_TAG"

val settingKodeinModule = Kodein.Module(SETTING_MODULE_TAG) {
    //Model
    bind<SettingModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        SettingModel(instance())
    }
    //ViewModel
    bind<SettingViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, SettingViewModelFactory(model = instance())
        ).get(SettingViewModel::class.java)
    }

    //Adapter
    bind<SettingAdapter>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton { SettingAdapter() }
}
