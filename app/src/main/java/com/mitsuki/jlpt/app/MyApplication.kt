package com.mitsuki.jlpt.app

import android.app.Application
import com.mitsuki.jlpt.db.DataReference

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DBImport.importDatabase(this)
        DataReference.init(this@MyApplication)
    }
}