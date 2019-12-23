package com.mitsuki.jlpt.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mitsuki.jlpt.R
import com.mitsuki.jlpt.base.AutoDisposeViewHolder
import com.mitsuki.jlpt.base.BaseAdapter
import com.mitsuki.jlpt.entity.Word
import com.mitsuki.jlpt.entity.WordState

class TestingAdapter : BaseAdapter<Word, TestingAdapter.MyViewHolder>() {

    var confirmEvent: ((() -> WordState) -> Unit)? = null

    var judgeTag = false

    override fun onMyCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_testing, parent, false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onMyBindViewHolder(t: MyViewHolder, i: Int) {
        t.questionView.text = getItem(i).cn
        t.stateLayout.visibility = View.GONE
        t.answerView.setText("")
        if (i == 0) {
            t.checkBtn.setOnClickListener {
                if (t.stateLayout.visibility == View.GONE) {
                    confirmEvent?.invoke {
                        t.answerView.clearFocus()
                        t.itemView.context
                        if (t.answerView.text.toString() == getItem(i).kana
                            || (!judgeTag && t.answerView.text.toString() == getItem(i).jp)) {
                            t.stateView.isChecked = true
                            t.stateView.text = ""
                        } else {
                            t.stateView.isChecked = false
                            t.stateView.text = "${getItem(i).jp}\n${getItem(i).kana}"
                        }
                        t.stateLayout.visibility = View.VISIBLE
                        WordState(getItem(i).id, fav = false, visible = !t.stateView.isChecked)
                    }
                }
            }
        }
    }

    inner class MyViewHolder(view: View) : AutoDisposeViewHolder(view) {
        val numberView: TextView = itemView.findViewById(R.id.number)
        val questionView: TextView = itemView.findViewById(R.id.testingQuestion)
        val answerView: EditText = itemView.findViewById(R.id.testingAnswer)
        val checkBtn: Button = itemView.findViewById(R.id.testingCheck)

        val stateLayout: FrameLayout = itemView.findViewById(R.id.testingStateLayout)
        val stateView: CheckBox = itemView.findViewById(R.id.testingState)
    }

    fun add(data: Word) {
        mData.add(data)
    }

    fun remove(index: Int) {
        mData.removeAt(index)
    }
}