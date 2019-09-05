package com.mitsuki.jlpt.base

import android.os.Bundle

interface IActivity {
    fun initView(savedInstanceState: Bundle?): Int
    fun initData(savedInstanceState: Bundle?)
}
