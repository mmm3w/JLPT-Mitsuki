package com.mitsuki.jlpt.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.mitsuki.jlpt.app.constants.Constants
import com.mitsuki.jlpt.app.tts.Speaker
import com.mitsuki.jlpt.app.tts.TTSFactory
import com.mitsuki.jlpt.db.MyDataBase
import com.mitsuki.jlpt.model.SPRepository
import io.reactivex.plugins.RxJavaPlugins
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
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

        //okhttp
        bind<SimpleRequest>() with singleton { SimpleRequest() }

        bind<SharedPreferences>(SHARED_PREFERENCES_NAME) with singleton {
            INSTANCE.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        }

        //setting Repository
        bind<SPRepository>() with singleton { SPRepository.getInstance(instance(SHARED_PREFERENCES_NAME)) }
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