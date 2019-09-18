package com.mitsuki.jlpt.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseFragment
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.module.numeralSortKodeinModule
import com.mitsuki.jlpt.ui.adapter.NumeralSortAdapter
import com.mitsuki.jlpt.viewmodel.NumeralViewModel
import com.mitsuki.jlpt.viewmodel.NumeralViewState
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_numeral.*
import org.kodein.di.Kodein

class NumeralSortFragment : BaseFragment<NumeralViewModel>() {
    override val kodeinModule: Kodein.Module = numeralSortKodeinModule
    override val viewModel: NumeralViewModel by lazy {
        ViewModelProviders.of(activity!!).get(NumeralViewModel::class.java)
    }

    private val mAdapter = NumeralSortAdapter()

    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_numeral, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.getDataObservable().autoDisposable(scopeProvider).subscribe(this::onDataEvent)
    }

    private fun initRecyclerView() {
        numeralList.layoutManager = LinearLayoutManager(context)
        numeralList.adapter = mAdapter
        mAdapter.getItemClickEvent().autoDisposable(scopeProvider).subscribe(this::onAdapterEvent)
    }

    private fun onDataEvent(data: NumeralViewState) {
        data.numeralSort?.let {
            mAdapter.addAll(it)
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun onAdapterEvent(data: NumeralSort) {
        viewModel.getNumeralDetail(data)
        Navigation.findNavController(activity!!, R.id.my_nav_host_fragment)
            .navigate(R.id.action_numeralSortFragment_to_numeralDetailFragment2)
    }
}