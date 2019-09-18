package com.mitsuki.jlpt.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseFragment
import com.mitsuki.jlpt.module.numeralDetailKodeinModule
import com.mitsuki.jlpt.ui.adapter.NumeralDetailAdapter
import com.mitsuki.jlpt.ui.adapter.WordAdapter
import com.mitsuki.jlpt.viewmodel.NumeralViewModel
import com.mitsuki.jlpt.viewmodel.NumeralViewState
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_numeral.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class NumeralDetailFragment : BaseFragment<NumeralViewModel>() {
    override val kodeinModule: Kodein.Module = numeralDetailKodeinModule
    override val viewModel: NumeralViewModel by lazy {
        ViewModelProviders.of(activity!!).get(NumeralViewModel::class.java)
    }
    private val mAdapter = NumeralDetailAdapter()

    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_numeral, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.getDataObservable().throttleFirst(1, TimeUnit.SECONDS)
            .autoDisposable(scopeProvider).subscribe(this::onDataEvent)
    }

    private fun initRecyclerView() {
        numeralList.layoutManager = LinearLayoutManager(context)
        numeralList.adapter = mAdapter
    }

    private fun onDataEvent(data: NumeralViewState) {
        data.words?.let {
            mAdapter.addAll(it)
            mAdapter.notifyDataSetChanged()
        }
    }
}