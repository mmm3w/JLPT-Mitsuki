package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.TestingSettingModel
import com.mitsuki.jlpt.viewmodel.TestingSettingViewModel
import com.mitsuki.jlpt.viewmodel.TestingSettingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val TESTING_SETTING_MODULE_TAG = "TESTING_SETTING_MODULE_TAG"

val testingSettingKodeinModule = Kodein.Module(TESTING_SETTING_MODULE_TAG) {
    //Model
    bind<TestingSettingModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        TestingSettingModel(spRepository = instance())
    }

    //ViewModel
    bind<TestingSettingViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, TestingSettingViewModelFactory(model = instance())
        ).get(TestingSettingViewModel::class.java)
    }
}
