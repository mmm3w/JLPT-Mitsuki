package com.mitsuki.jlpt.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mitsuki.jlpt.ui.adapter.NumeralDetailAdapter
import com.mitsuki.jlpt.ui.adapter.NumeralSortAdapter
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.NumeralViewModel
import com.mitsuki.jlpt.viewmodel.NumeralViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val NUMERAL_DETAIL_MODULE_TAG = "NUMERAL_DETAIL_MODULE_TAG"

val numeralDetailKodeinModule = Kodein.Module(NUMERAL_DETAIL_MODULE_TAG) {

}