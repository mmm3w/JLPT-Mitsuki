package com.mitsuki.jlpt.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.mitsuki.jlpt.model.MainModel
import com.mitsuki.jlpt.ui.adapter.NumeralAdapter
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.MainViewModel
import com.mitsuki.jlpt.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

val mainKodeinModule = Kodein.Module(MAIN_MODULE_TAG) {
    //Model
    bind<MainModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        MainModel(
            db = instance(),
            config = instance()
        )
    }
    //ViewModel
    bind<MainViewModel>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(
            context.viewModelStore,
            MainViewModelFactory(model = instance())
        ).get(MainViewModel::class.java)
    }

    //Adapter
    bind<WordAdapter>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton { WordAdapter() }
    bind<NumeralAdapter>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton { NumeralAdapter() }
    //paging list config
    bind<PagedList.Config>() with scoped<FragmentActivity>(AndroidLifecycleScope).singleton {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false).build()
    }

}