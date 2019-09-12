package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.DataCheckModel
import com.mitsuki.jlpt.viewmodel.DataCheckViewModel
import com.mitsuki.jlpt.viewmodel.DataCheckViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


const val DATA_CHECK_MODULE_TAG = "DATA_CHECK_MODULE_TAG"

val dataCheckKodeinModule = Kodein.Module(DATA_CHECK_MODULE_TAG) {
    //Model
    bind<DataCheckModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        DataCheckModel(db = instance())
    }
    //ViewModel
    bind<DataCheckViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, DataCheckViewModelFactory(model = instance())
        ).get(DataCheckViewModel::class.java)
    }
}
