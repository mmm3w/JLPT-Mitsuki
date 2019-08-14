package com.mitsuki.jlpt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mitsuki.jlpt.db.MyDataBase

class MainViewModel(db: MyDataBase) : ViewModel() {
    val allWord = LivePagedListBuilder(
            db.wordDao().queryWords(),
            PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).setInitialLoadSizeHint(10).build()
    ).build()
}

class MainViewModelFactory(private val db: MyDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel(db) as T
}