package com.mitsuki.jlpt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

class MainViewModel(db: MyDataBase) : ViewModel() {

    val db = db

    val allWord = LivePagedListBuilder(
        db.wordDao().queryWordsWithVisible(),
        PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).setInitialLoadSizeHint(10).build()
    ).build()

    var undoCache: WordState? = null

    fun hideWord(word: Word?) {
        word?.also {
            val s = WordState(it.id, fav = false, visible = false)
            db.stateDao().insert(s)
            undoCache = s
            undoCache?.visible = true
        }
    }

    fun undoOperation() {
        undoCache?.also {
            db.stateDao().insert(it)
        }
    }
}

class MainViewModelFactory(private val db: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(db) as T
}