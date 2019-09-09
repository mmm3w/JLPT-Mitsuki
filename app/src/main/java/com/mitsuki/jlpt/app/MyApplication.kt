package com.mitsuki.jlpt.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mitsuki.jlpt.app.constants.Constants
import com.mitsuki.jlpt.db.MyDataBase
import io.reactivex.plugins.RxJavaPlugins
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        //基础组件
        bind<Context>() with singleton { this@MyApplication }
        import(androidCoreModule(this@MyApplication))
        import(androidXModule(this@MyApplication))

        //Room
        bind<MyDataBase>() with singleton {
            Room.databaseBuilder(
                this@MyApplication, MyDataBase::class.java, Constants.dbFile(this@MyApplication)
            ).build()
        }

        bind<SharedPreferences>(SHARED_PREFERENCES_NAME) with singleton {
            MyApplication.INSTANCE.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        }


    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        DBImport.importDatabase(this)
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}