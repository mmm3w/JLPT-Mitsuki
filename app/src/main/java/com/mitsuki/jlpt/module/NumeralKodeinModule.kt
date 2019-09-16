package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mitsuki.jlpt.model.NumeralModel
import com.mitsuki.jlpt.viewmodel.NumeralViewModel
import com.mitsuki.jlpt.viewmodel.NumeralViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val NUMERAL_MODULE_TAG = "NUMERAL_MODULE_TAG"

val numeralKodeinModule = Kodein.Module(NUMERAL_MODULE_TAG){
    //Model
    bind<NumeralModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        NumeralModel()
    }
    //ViewModel
    bind<NumeralViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore, NumeralViewModelFactory(model = instance())
        ).get(NumeralViewModel::class.java)
    }
}