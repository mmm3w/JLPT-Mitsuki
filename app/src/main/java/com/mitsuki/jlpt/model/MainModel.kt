package com.mitsuki.jlpt.model

import android.annotation.SuppressLint
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mitsuki.jlpt.app.SimpleRequest
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.uber.autodispose.autoDisposable
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable as Observable

class MainModel(
    private val simpleRequest: SimpleRequest,
    private val db: MyDataBase,
    private val config: PagedList.Config
) : BaseModel() {

    private val flowableMap: HashMap<String, Flowable<PagedList<Word>>> = HashMap()

    //隐藏单词
    fun modifyWordState(ws: WordState) {
        db.stateDao().insert(ws)
    }

    //全部
    fun fetchAllWord(): Flowable<PagedList<Word>> {
        flowableMap["fetchAllWord"]?.let { return it }
        val temp =
            RxPagedListBuilder(db.wordDao().queryWordsWithVisible(), config).setFetchScheduler(
                Schedulers.io()
            ).buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchAllWord"] = temp
        return temp
    }

    fun fetchWord(kind: Int): Flowable<PagedList<Word>> {
        flowableMap["fetchWord$kind"]?.let { return it }
        val temp =
            RxPagedListBuilder(db.wordDao().queryWordsWithVisible(kind), config).setFetchScheduler(
                Schedulers.io()
            ).buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWord$kind"] = temp
        return temp
    }

    //已隐藏单词
    fun fetchWordWithInvisible(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithInvisible"]?.let { return it }
        val temp =
            RxPagedListBuilder(db.wordDao().queryWordsWithInvisible(), config).setFetchScheduler(
                Schedulers.io()
            ).buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithInvisible"] = temp
        return temp
    }

    @SuppressLint("CheckResult")
    fun requestVersion(): Observable<Int> {
        return Observable.create<Int> { emitter ->
            simpleRequest.requestVersion {
                emitter.onNext(it)
                emitter.onComplete()
            }
        }
    }

}