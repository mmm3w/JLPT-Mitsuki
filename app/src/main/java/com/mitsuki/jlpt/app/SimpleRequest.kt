package com.mitsuki.jlpt.app

import com.mitsuki.jlpt.app.constants.Constants
import okhttp3.*
import java.io.IOException

class SimpleRequest {
    private val client = OkHttpClient()

    fun requestVersion(callback: (v: Int) -> Unit) {
        val request = Request.Builder().url(Constants.UPDATE_VERSION).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.invoke(-1)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    callback.invoke((response.body?.string() ?: "-1").toInt())
                } catch (e: Exception) {
                    callback.invoke(-1)
                }
            }
        })
    }

    fun requestWord(callback: (isSuccess: Boolean, str: String) -> Unit) {
        val request = Request.Builder().url(Constants.UPDATE_FILE).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.invoke(false, e.message ?: "")
            }

            override fun onResponse(call: Call, response: Response) {
                callback.invoke(true, response.body?.string() ?: "")
            }
        })
    }


}