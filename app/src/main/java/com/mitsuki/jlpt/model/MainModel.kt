package com.mitsuki.jlpt.model

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mitsuki.jlpt.base.BaseModel
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Numeral
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class MainModel(private val db: MyDataBase, private val config: PagedList.Config):BaseModel() {

    private val flowableMap: HashMap<String, Flowable<PagedList<Word>>> = HashMap()

    //隐藏单词
    fun modifyWordState(ws: WordState) {
        db.stateDao().insert(ws)
    }

    //全部
    fun fetchAllWord(): Flowable<PagedList<Word>> {
        flowableMap["fetchAllWord"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithVisible(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchAllWord"] = temp
        return temp
    }

    //N5
    fun fetchWordWithN5(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithN5"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithN5(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithN5"] = temp
        return temp
    }

    //N4
    fun fetchWordWithN4(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithN4"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithN4(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithN4"] = temp
        return temp
    }

    //N3
    fun fetchWordWithN3(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithN3"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithN3(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithN3"] = temp
        return temp
    }

    //N2
    fun fetchWordWithN2(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithN2"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithN2(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithN2"] = temp
        return temp
    }

    //N1
    fun fetchWordWithN1(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithN1"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithN1(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithN1"] = temp
        return temp
    }

    //数词量词
    fun fetchWordWithNumeral(): Flowable<PagedList<Numeral>> {
        return RxPagedListBuilder(db.numeralDao().queryWords(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
    }

    //已隐藏单词
    fun fetchWordWithInvisible(): Flowable<PagedList<Word>> {
        flowableMap["fetchWordWithInvisible"]?.let { return it }
        val temp = RxPagedListBuilder(db.wordDao().queryWordsWithInvisible(), config)
            .setFetchScheduler(Schedulers.io())
            .buildFlowable(BackpressureStrategy.LATEST)
        flowableMap["fetchWordWithInvisible"] = temp
        return temp
    }

}