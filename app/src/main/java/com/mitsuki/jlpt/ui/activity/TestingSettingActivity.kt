package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.module.testingSettingKodeinModule
import com.mitsuki.jlpt.ui.adapter.DefaultSettingAdapter
import com.mitsuki.jlpt.viewmodel.TestingSettingViewModel
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TestingSettingActivity : BaseActivity<TestingSettingViewModel>() {
    override val kodeinModule: Kodein.Module = testingSettingKodeinModule
    override val viewModel: TestingSettingViewModel by instance()

    private val mAdapter = DefaultSettingAdapter()


    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_testing_setting

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()
        initSubscription()
        viewModel.getSetting()
    }

    /**********************************************************************************************/
    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "单词测试设置"
    }


    @SuppressLint("CheckResult")
    private fun initComponent() {
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = mAdapter
    }

    private fun initSubscription() {
        viewModel.viewModelObservable().autoDisposable(scopeProvider)
            .subscribe(this::onViewStateEvent)
        mAdapter.getItemClickEvent().autoDisposable(scopeProvider).subscribe(this::onSettingEvent)
    }

    /**********************************************************************************************/
    private fun onViewStateEvent(state: TestingSettingViewModel.TestingSettingViewState) {
        state.list?.apply {
            mAdapter.addAll(this)
            mAdapter.notifyDataSetChanged()
        }

        state.displayList?.apply {
            showSelectDialog(state.selection, this, state.selectType!!)
        }
        state.kindList?.apply {
            showSelectDialog(state.selection, this, state.selectType!!)
        }
    }

    private fun onSettingEvent(setting: Setting) {
        when (setting.type) {
            Setting.SETTING_TESTING_DISPLAY -> viewModel.onDisplaySelectEvent()
            Setting.SETTING_TESTING_KIND -> viewModel.onKindSelectEvent()
        }
    }

    /**********************************************************************************************/
    private fun showSelectDialog(selection: Int, list: List<String>, type: Int) {
        MaterialDialog(this).show {
            listItemsSingleChoice(
                items = list, initialSelection = selection
            ) { _: MaterialDialog, _: Int, str: CharSequence ->
                viewModel.onSelectEvent(type, str.toString())
            }
            lifecycleOwner(this@TestingSettingActivity)
        }
    }
}