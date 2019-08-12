package com.mitsuki.jlpt.app

import android.os.Environment

object Constants {
    val DATABASE_FOLDER = Environment.getExternalStorageDirectory().path + "/JLPT"
    val DATABASE_FILE = "$DATABASE_FOLDER/jlpt.db"
}