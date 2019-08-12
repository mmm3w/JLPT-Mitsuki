package com.mitsuki.jlpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitsuki.jlpt.app.DBImport
import com.mitsuki.jlpt.db.DataReference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataReference.wordDao().queryWords()
    }
}
