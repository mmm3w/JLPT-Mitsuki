package com.mitsuki.jlpt.model

import android.annotation.SuppressLint
import android.os.Message
import android.util.Log
import com.mitsuki.jlpt.app.SimpleRequest
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.entity.Word
import io.reactivex.Observable
import kotlinx.coroutines.newFixedThreadPoolContext

class SettingModel(
    private val simpleRequest: SimpleRequest,
    private val spRepository: SPRepository,
    private val db: MyDataBase
) : BaseModel() {
    fun getSetting(): List<Setting> {
        val ttsSetting = Setting("TTS设置", "TTS方式选择、TTS设置、TTS发声示例")
        val updateSetting = Setting("更新词表", "联网更新所有单词表")
        val dataDebugSetting = Setting("数据调试", "测试功能")
        return arrayListOf(updateSetting, ttsSetting, dataDebugSetting)
    }


    @SuppressLint("CheckResult")
    fun requestVersion(): Observable<Int> {
        return Observable.create<Int> { emitter ->
            simpleRequest.requestVersion {
                spRepository.tempWordVersion = it
                emitter.onNext(if (it > spRepository.wordVersion) it else -1)
                emitter.onComplete()
            }
        }
    }

    fun requestData(): Observable<Message> {
        return Observable.create<Message> { emitter ->
            var message = Message.obtain()
            simpleRequest.requestWord { isSuccess, str ->
                if (isSuccess) {
                    message.what = 0
                    emitter.onNext(message)

                    updateDatabase(str)

                    message.what = 1
                    emitter.onNext(message)
                    spRepository.wordVersion = spRepository.tempWordVersion
                    emitter.onComplete()
                } else {
                    message.what = -1
                    message.obj = str
                    emitter.onNext(message)
                    emitter.onComplete()
                }
            }
        }
    }

    private fun updateDatabase(str: String) {
        val wordsList = ArrayList<Word>()
        val firstDismantling = str.split("\n")
        for (unit in firstDismantling) {
            try {
                val twig = unit.split("|")
                wordsList.add(Word(twig[0].toInt(), twig[2], twig[1], twig[3], twig[4].toInt()))
            } catch (e: Exception) {
            }
        }
        db.wordDao().updateWords(wordsList)
    }

}