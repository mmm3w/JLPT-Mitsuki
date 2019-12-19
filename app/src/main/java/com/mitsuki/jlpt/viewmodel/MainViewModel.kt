package com.mitsuki.jlpt.viewmodel

import android.annotation.SuppressLint
import androidx.paging.PagedList
import com.mitsuki.jlpt.app.kind.Kind
import com.mitsuki.jlpt.app.kind.KindMode
import com.mitsuki.jlpt.base.BaseViewModel
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState
import com.mitsuki.jlpt.model.MainModel
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.PublishSubject

@Suppress("MoveSuspiciousCallableReferenceIntoParentheses")
@SuppressLint("CheckResult")
class MainViewModel(private val model: MainModel) : BaseViewModel() {
    private val dataProcessor: BehaviorProcessor<PagedList<Word>> = BehaviorProcessor.create()
    private val eventSubject: PublishSubject<ViewState> = PublishSubject.create()

    private var undoCache: WordState? = null
    private var disposable: Disposable? = null

    private var wordKind = Kind.getKind(model.obtainWordKind())
    private var snackBol = false
    private var lastModify = 0

    fun observeData(): Flowable<PagedList<Word>> = dataProcessor.hide()

    fun observeEvent(): Observable<ViewState> = eventSubject.hide()

    //切换词库
    fun switchMode(mode: Int, isInitial: Boolean = false) {
        this.lastModify = -1

        if (!isInitial) {
            wordKind = Kind.getKind(mode)
        }

        with(wordKind) {
            disposable?.dispose()
            disposable = when (getMode()) {
                Kind.ALL -> onPushData(model.fetchAllWord())
                Kind.N1 -> onPushData(model.fetchWord(getMode()))
                Kind.N2 -> onPushData(model.fetchWord(getMode()))
                Kind.N3 -> onPushData(model.fetchWord(getMode()))
                Kind.N4 -> onPushData(model.fetchWord(getMode()))
                Kind.N5 -> onPushData(model.fetchWord(getMode()))
                Kind.INVISIBLE -> onPushData(model.fetchWordWithInvisible())
                else -> null
            }
            if (getMode() >= 0) {
                model.updateWordKind(getMode())
                eventSubject.onNext(ViewState(kind = this))
            }
        }
    }

    //改变单词可见状态
    fun changeWordState(position: Int, word: Word?) {
        snackBol = true
        lastModify = position
        word?.also {
            val s = WordState(it.id, fav = false, visible = wordKind.getMode() == Kind.INVISIBLE)
            model.modifyWordState(s)
            undoCache = s
            undoCache?.visible = !s.visible
        }
    }

    //撤销操作
    fun undoOperation() {
        undoCache?.also { model.modifyWordState(it) }
    }

    //数据加载完后的操作
    fun checkListStatus() {
        if (snackBol) {
            //隐藏单词更新列表后需要显示snack bar
            snackBol = false
            eventSubject.onNext(ViewState(normalEvent = MainEvent.SHOW_SNACKBAR))
        } else {
            //撤销第一个item的删除时更新列表需要回到顶部
            if (lastModify == 0) eventSubject.onNext(ViewState(normalEvent = MainEvent.SCROLL_TO_TOP))
        }
    }

    //检查更新
    fun checkWordVersion() {
        model.requestVersion().filter { it != -1 }.autoDisposable(this)
            .subscribe { eventSubject.onNext(ViewState(MainEvent.NEW_WORD_VERSION)) }
    }

    data class ViewState(val normalEvent: MainEvent? = null, val kind: KindMode? = null)

    private fun onPushData(flowable: Flowable<PagedList<Word>>) =
        flowable.autoDisposable(this).subscribe { dataProcessor.onNext(it) }
}

enum class MainEvent {
    SHOW_SNACKBAR, SCROLL_TO_TOP, NEW_WORD_VERSION
}

