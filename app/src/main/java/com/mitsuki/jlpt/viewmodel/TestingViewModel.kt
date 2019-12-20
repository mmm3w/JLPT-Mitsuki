package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.model.TestingModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TestingViewModel(private val model: TestingModel) : BaseViewModel() {

    private val subject: PublishSubject<TestingViewState> = PublishSubject.create()

    private var lastWordState: WordState? = null

    //加载最初的数据
    //获取是否包含隐藏单词
    //获取当前选的词库
    //自动获取一定量的单词
    fun initTestData() {
        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(this).subscribe {
            subject.onNext(TestingViewState(initialData = model.obtainInitialData()))
        }
    }

    //卡片划去的时候将上次的结果存入数据库
    //并加载下一条数据
    fun updateTestData() {
        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(this).subscribe {

            lastWordState?.apply {
                //写入数据
                model.saveWordState(this)
            }
            lastWordState = null

            subject.onNext(
                TestingViewState(
                    word = model.obtainAdditionalData() ?: Word(-1, "", "", "", -1)
                )
            )
        }
    }

    //将测试完的单词结果缓存
    fun cachingLastTestingResult(state: WordState) {
        lastWordState = state
    }

    fun getViewModelObservable(): Observable<TestingViewState> =
        subject.hide().observeOn(AndroidSchedulers.mainThread())

    data class TestingViewState(
        val initialData: MutableList<Word>? = null, val word: Word? = null
    )
}