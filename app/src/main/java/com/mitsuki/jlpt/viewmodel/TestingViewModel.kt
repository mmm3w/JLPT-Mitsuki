package com.mitsuki.jlpt.viewmodel

import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.model.TestingModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TestingViewModel(private val model: TestingModel) : BaseViewModel() {

    private val subject: PublishSubject<TestingViewState> = PublishSubject.create()


    //加载最初的数据
    fun initTestData() {
        //获取是否包含隐藏单词
        //获取当前选的词库
        //自动获取一定量的单词

        //        model.


        Completable.fromAction {}.observeOn(Schedulers.io()).autoDisposable(this).subscribe {
            val d = model.obtainInitialData()
            d[0]
        }
    }

    //追加数据
    fun addToTest() {

    }

    fun getViewModelObservable() = subject.hide()


    data class TestingViewState(
        val initialData: MutableList<Word>? = null, val word: Word? = null
    )
}