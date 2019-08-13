package com.mitsuki.jlpt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.db.MyDataBase
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
