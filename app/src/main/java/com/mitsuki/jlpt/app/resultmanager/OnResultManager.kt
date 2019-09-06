package com.mitsuki.jlpt.app.resultmanager

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object OnResultManager {
    var tag = javaClass.simpleName

    private fun findOnResultFragment(activity: FragmentActivity): ResultFragment? =
        activity.supportFragmentManager.findFragmentByTag(tag)?.let { it as ResultFragment }


    private fun getOnResultFragment(activity: FragmentActivity): ResultFragment {
        findOnResultFragment(activity)?.let {
            return it
        }

        val fragment = ResultFragment()
        activity.supportFragmentManager
            .beginTransaction()
            .add(fragment, tag)
            .commitAllowingStateLoss()
        activity.supportFragmentManager
            .executePendingTransactions()
        return fragment
    }

    fun startActivityForResult(
        fragment: Fragment,
        intent: Intent,
        requestCode: Int,
        callback: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
    ) {
        startActivityForResult((fragment.activity ?: return), intent, requestCode, callback)
    }

    fun startActivityForResult(
        activity: FragmentActivity,
        intent: Intent,
        requestCode: Int,
        callback: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
    ) {
        getOnResultFragment(activity).startActivityForResult(intent, requestCode, callback)
    }


    fun requestPermissions(
        fragment: Fragment,
        permissions: Array<out String>,
        requestCode: Int,
        callback: (requestCode: Int, permissions: Array<String>, grantResults: IntArray) -> Unit
    ) {
        requestPermissions((fragment.activity ?: return), permissions, requestCode, callback)
    }

    fun requestPermissions(
        activity: FragmentActivity,
        permissions: Array<out String>,
        requestCode: Int,
        callback: (requestCode: Int, permissions: Array<String>, grantResults: IntArray) -> Unit
    ) {
        getOnResultFragment(activity).requestPermissions(permissions, requestCode, callback)
    }


}