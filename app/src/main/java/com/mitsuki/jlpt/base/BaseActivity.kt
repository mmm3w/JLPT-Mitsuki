package com.mitsuki.jlpt.base

import android.os.Bundle
import android.view.InflateException
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.kcontext

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(), IActivity, KodeinAware {

    val TAG = javaClass.simpleName

    protected val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
    }

    override val kodeinContext: KodeinContext<*> = kcontext(this)
    abstract val kodeinModule: Kodein.Module
    abstract val viewModel: T

    private val parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
        import(kodeinModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initView(savedInstanceState).apply { if (this != 0) setContentView(this) }
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }
        initData(savedInstanceState)
    }
}