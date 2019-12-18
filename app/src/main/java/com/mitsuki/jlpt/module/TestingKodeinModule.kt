package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.TestingModel
import com.mitsuki.jlpt.ui.adapter.TestingAdapter
import com.mitsuki.jlpt.viewmodel.TestingViewModel
import com.mitsuki.jlpt.viewmodel.TestingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val TESTING_MODULE_TAG = "TESTING_MODULE_TAG"

val testingKodeinModule = Kodein.Module(TESTING_MODULE_TAG) {
    //Model
    bind<TestingModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        TestingModel()
    }

    //ViewModel
    bind<TestingViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, TestingViewModelFactory(model = instance())
        ).get(TestingViewModel::class.java)
    }
}