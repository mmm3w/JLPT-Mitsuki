package com.mitsuki.jlpt.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.constants.TTS_KIND
import com.mitsuki.jlpt.app.getInt
import com.mitsuki.jlpt.app.putInt
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseActivity
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.module.settingKodeinModule
import com.mitsuki.jlpt.ui.adapter.SettingAdapter
import com.mitsuki.jlpt.viewmodel.SettingViewModel
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_setting.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

@SuppressLint("Registered")
class SettingActivity : BaseActivity<SettingViewModel>() {

    override val kodeinModule: Kodein.Module = settingKodeinModule

    override val viewModel: SettingViewModel by instance()
    private val mAdapter: SettingAdapter by instance()

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_setting

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar()
        initComponent()
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
            "更新词表" -> {
            }
            "TTS设置" -> showTTSDialog()
            "数据调试" -> startActivity(Intent(this, DataCheckActivity::class.java))
        }
    }

    private fun showTTSDialog() {
        //TODO:待完善，在Google translate TTS实现之前暂时不使用该功能
//        MaterialDialog(this).show {
//            title(text = "TTS设置")
//            listItemsSingleChoice(
//                items = TTSFactory.list(),
//                initialSelection = TTSFactory.list().indexOf(TTSFactory.ttsStr(getInt(TTS_KIND))) + 1
//            ) { _: MaterialDialog, _: Int, str: CharSequence ->
//                putInt(TTS_KIND, TTSFactory.ttsInt(str.toString()))
//            }
//            lifecycleOwner(this@SettingActivity)
//        }
    }

}