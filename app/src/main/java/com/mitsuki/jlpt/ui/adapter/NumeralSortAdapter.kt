package com.mitsuki.jlpt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.app.clicksThrottleFirst
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.NumeralSort
import com.mitsuki.jlpt.entity.Setting
import com.mitsuki.jlpt.entity.Word
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class NumeralSortAdapter : BaseAdapter<NumeralSort, NumeralSortAdapter.MyViewHolder>() {

    private val subject: PublishSubject<NumeralSort> = PublishSubject.create()

    override fun onMyCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder =
        MyViewHolder(viewGroup)

    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.nameView.text = getItem(i).title
        t.itemView.setOnClickListener {
            subject.onNext (getItem(i))
        }
    }

    fun getItemClickEvent(): Observable<NumeralSort> {
        return subject.throttleFirst(2,TimeUnit.SECONDS).hide()
    }

    inner class MyViewHolder(parent: ViewGroup) : AutoDisposeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_numeral_sort, parent, false)
    ) {
        val nameView: TextView = itemView.findViewById(R.id.numeral_name)
    }
}