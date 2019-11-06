package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.hint.toastShort
import com.mitsuki.jlpt.app.tts.SpeakUtils
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.module.ttsSettingKodeinModule
import com.mitsuki.jlpt.ui.adapter.SettingAdapter
import com.mitsuki.jlpt.viewmodel.TTSSettingViewModel
import com.mitsuki.jlpt.viewmodel.TTSSettingViewState
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


class TTSSettingActivity : BaseActivity<TTSSettingViewModel>() {
    override val kodeinModule: Kodein.Module = ttsSettingKodeinModule
    override val viewModel: TTSSettingViewModel by instance()

    private val mAdapter = SettingAdapter()

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_tts_setting

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()

        viewModel.observeEvent().autoDisposable(scopeProvider).subscribe(this::onViewModelEvent)

        viewModel.getSetting()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        title = "TTS设置"
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = mAdapter
        mAdapter.getItemClickEvent().autoDisposable(scopeProvider).subscribe(this::onSettingEvent)
    }

    private fun onViewModelEvent(viewState: TTSSettingViewState) {
        viewState.items?.let {
            mAdapter.addAll(it)
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun onSettingEvent(setting: Setting) {
        when (setting.text) {
            "TTS选择" -> showTTSSelectDialog(setting.ext as Int)
            "TTS设置" -> launchTTSSetting()
            "TTS测试" -> SpeakUtils.speak(
                this,
                "ttsSample",
                resources.getString(R.string.tts_sample)
            ) { toastShort { it } }
        }
    }

    private fun showTTSSelectDialog(kind: Int) {
        MaterialDialog(this).show {
            title(text = "TTS选择")
            listItemsSingleChoice(
                items = TTSFactory.list(),
                initialSelection = TTSFactory.list().indexOf(TTSFactory.ttsStr(kind))
            ) { _: MaterialDialog, _: Int, str: CharSequence ->
                viewModel.replaceTTS(TTSFactory.ttsInt(str.toString()))
            }
            lifecycleOwner(this@TTSSettingActivity)
        }
    }

    private fun launchTTSSetting() {
        Intent().apply {
            action = "com.android.settings.TTS_SETTINGS"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }
}