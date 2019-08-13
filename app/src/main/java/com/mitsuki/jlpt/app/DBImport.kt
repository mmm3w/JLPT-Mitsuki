package com.mitsuki.jlpt.app

import android.content.Context
import android.os.Environment
import android.system.Os.read
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object DBImport {

    fun importDatabase(context: Context) {
        if (File(Constants.DATABASE_FILE).exists()) return

        var folder = File(Constants.DATABASE_FOLDER)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        try {
            val fileNames = context.assets.list("db")
            for (name in fileNames) {
                val inStream = context.assets.open("db/$name")
                val outStream = FileOutputStream(Constants.DATABASE_FOLDER + "/$name")
                inStream.use { input ->
                    outStream.use { output ->
                        input.copyTo(output)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

//    fun exportDatabaseFile() {
//        var name = ""
//        try {
//            copyDataFromOneToAnother(
//                "/data/data/com.mitsuki.jlpt/databases/$name",
//                Environment.getExternalStorageDirectory().path + "/$name"
//            )
//            copyDataFromOneToAnother(
//                "/data/data/com.mitsuki.jlpt/databases/$name-shm",
//                Environment.getExternalStorageDirectory().path + "/$name-shm"
//            )
//            copyDataFromOneToAnother(
//                "/data/data/com.mitsuki.jlpt/databases/$name-wal",
//                Environment.getExternalStorageDirectory().path + "/$name-wal"
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun copyDataFromOneToAnother(fromPath: String, toPath: String) {
//        val inStream = File(fromPath).inputStream()
//        val outStream = FileOutputStream(toPath)
//
//        inStream.use { input ->
//            outStream.use { output ->
//                input.copyTo(output)
//            }
//        }
//    }

}