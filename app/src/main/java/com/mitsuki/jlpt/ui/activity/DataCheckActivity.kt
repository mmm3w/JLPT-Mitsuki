package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.module.dataCheckKodeinModule
import com.mitsuki.jlpt.viewmodel.DataCheckViewModel
import com.mitsuki.jlpt.viewmodel.DataCheckViewState
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_data_check.*
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance

class DataCheckActivity : BaseActivity<DataCheckViewModel>() {
    override val kodeinModule = dataCheckKodeinModule

    override val viewModel: DataCheckViewModel by instance()

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_data_check

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        showData()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "数据调试"
    }

    private fun showData() {
        viewModel.getDataObservable().autoDisposable(scopeProvider)
            .subscribe(this::onViewModelEvent)
        viewModel.checkDataNumber()
    }

    private fun onViewModelEvent(data: DataCheckViewState) {
        when (data.kind) {
            Kind.N1 -> loadNumber(n1WordData, data)
            Kind.N2 -> loadNumber(n2WordData, data)
            Kind.N3 -> loadNumber(n3WordData, data)
            Kind.N4 -> loadNumber(n4WordData, data)
            Kind.N5 -> loadNumber(n5WordData, data)
            Kind.NUMERAL -> loadNumber(numeralWordData, data)
            Kind.INVISIBLE -> loadNumber(invisibleWordData, data)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadNumber(view: TextView, data: DataCheckViewState) {
        view.text = "${data.getKindMode().getTitle()}数：${data.number}"
    }
}