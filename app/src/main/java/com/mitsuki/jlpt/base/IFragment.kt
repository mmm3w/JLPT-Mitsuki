package com.mitsuki.jlpt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface IFragment {
    fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    fun initData(savedInstanceState: Bundle?)
}