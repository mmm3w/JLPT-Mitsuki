package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.hint.toastShort
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.module.settingKodeinModule
import com.mitsuki.jlpt.ui.adapter.SettingAdapter
import com.mitsuki.jlpt.viewmodel.SettingState
import com.mitsuki.jlpt.viewmodel.SettingViewModel
import com.mitsuki.jlpt.viewmodel.SettingViewState
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_setting.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("Registered")
class SettingActivity : BaseActivity<SettingViewModel>() {

    override val kodeinModule: Kodein.Module = settingKodeinModule

    override val viewModel: SettingViewModel by instance()
    private val mAdapter = SettingAdapter()

    val dialog by lazy { MaterialDialog(this) }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_setting

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()

        viewModel.observeEvent().autoDisposable(scopeProvider).subscribe(this::onUpdateEvent)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "设置"
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        mAdapter.addAll(viewModel.linkSetting())
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = mAdapter
        mAdapter.getItemClickEvent().autoDisposable(scopeProvider).subscribe { onSettingEvent(it) }
    }

    private fun onSettingEvent(setting: Setting) {
        when (setting.text) {
            "更新词表" -> viewModel.updateWords()
            "TTS设置" -> startActivity(Intent(this, TTSSettingActivity::class.java))
            "数据调试" -> startActivity(Intent(this, DataCheckActivity::class.java))
        }
    }

    private fun onUpdateEvent(state: SettingViewState) {
        when (state.state) {
            SettingState.REQUEST_VERSION -> dialog.show {
                message(text = "获取版本中...")
                lifecycleOwner(this@SettingActivity)
            }
            SettingState.HAVE_NEW_VERSION -> dialog.show {
                message(text = "获取数据中...")
                lifecycleOwner(this@SettingActivity)
            }
            SettingState.NO_NEW_VERSION -> {
                dialog.dismiss()
                toastShort { "已经是最新数据" }
            }
            SettingState.DOWNLOAD_DATA_ERROR -> {
                dialog.dismiss()
                toastShort { "数据获取失败:${state.msg}" }
            }
            SettingState.DOWNLOAD_DATA_SUCCESS -> dialog.show {
                message(text = "更新获取数据中...")
                lifecycleOwner(this@SettingActivity)
            }
            SettingState.UPDATE_DATABASE_SUCCESS -> {
                dialog.dismiss()
                toastShort { "数据更新成功" }
            }
        }
    }


}