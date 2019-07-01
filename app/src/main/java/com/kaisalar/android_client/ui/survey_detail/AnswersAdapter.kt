package com.kaisalar.android_client.ui.survey_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*

class AnswersAdapter(
    val context: Context,
    private val answers: List<AnswerForGetting>
) : RecyclerView.Adapter<AnswersAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater
            .from(context)
            .inflate(R.layout.answer_list_item, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return answers.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindAnswer(answers[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTitleTextView = itemView.findViewById<TextView>(R.id.questionTitleTextView)
        private val answerTextView = itemView.findViewById<TextView>(R.id.answerContentTextView)

        fun bindAnswer(answer: AnswerForGetting) {
            questionTitleTextView.text = answer.question.title
            when (answer.type) {
                ANSWER_TEXT -> {
                    val ans = answer as TextAnswerForGetting
                    answerTextView.text = ans.content.value
                }
                ANSWER_SINGLE_NUMBER_VALUE -> {
                    val ans = answer as SingleNumberValueAnswerForGetting
                    answerTextView.text = ans.content.value.toString()
                }
                ANSWER_MULTIPLE_CHOICE -> {
                    val ans = answer as MultipleChoiceAnswerForGetting
                    val strBuilder = StringBuilder()
                    for (c in ans.content.choices) {
                        if (ans.content.choices.indexOf(c) == ans.content.choices.count() - 1) {
                            strBuilder.append("- $c")
                        } else {
                            strBuilder.append("- $c\n")
                        }
                    }
                    answerTextView.text = strBuilder.toString()
                }
                ANSWER_RANGE -> {
                    val ans = answer as RangeAnswerForGetting
                    answerTextView.text = "${ans.content.minValue} ${26.toChar()} ${ans.content.maxValue}"
                }
            }
        }
    }
}