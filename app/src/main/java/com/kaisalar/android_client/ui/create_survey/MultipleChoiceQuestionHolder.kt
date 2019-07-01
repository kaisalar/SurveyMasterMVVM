package com.kaisalar.android_client.ui.create_survey

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*

class MultipleChoiceQuestionHolder(
    val context: Context,
    itemView: View,
    deleteOnClickListener: (QuestionForCreation) -> Unit
) : QuestionHolder(itemView, deleteOnClickListener) {

    private val choicesRecyclerView = itemView.findViewById<RecyclerView>(R.id.choicesRecyclerView)

    private val addChoiceButton = itemView.findViewById<MaterialButton>(R.id.addChoiceButton)

    override fun bindQuestion(question: QuestionForCreation) {
        super.bindQuestion(question)

        val castedQuestion = question as MultipleChoiceQuestion

        var deleteChoice: ((Int) -> Unit)? = null

        val adapter = when (castedQuestion.type) {
            QUESTION_RADIO_GROUP -> MultipleChoicesAdapter(
                context = context,
                choiceType = MultipleChoicesAdapter.CHOICE_RADIO_GROUP,
                choices = castedQuestion.content.choices,
                choiceOnTextChangeListener = { s, i ->
                    if (i < castedQuestion.content.choices.count() && i >= 0)
                        castedQuestion.content.choices[i] = s
                },
                deleteOnClickListener = {
                    deleteChoice?.invoke(it)
                }
            )
            QUESTION_CHECKBOX -> MultipleChoicesAdapter(
                context = context,
                choiceType = MultipleChoicesAdapter.CHOICE_CHECK_BOX,
                choices = castedQuestion.content.choices,
                choiceOnTextChangeListener = { s, i ->
                    if (i < castedQuestion.content.choices.count() && i >= 0)
                        castedQuestion.content.choices[i] = s
                },
                deleteOnClickListener = {
                    deleteChoice?.invoke(it)
                }
            )
            QUESTION_DROPDOWN -> MultipleChoicesAdapter(
                context = context,
                choiceType = MultipleChoicesAdapter.CHOICE_DROP_DOWN,
                choices = castedQuestion.content.choices,
                choiceOnTextChangeListener = { s, i ->
                    if (i < castedQuestion.content.choices.count() && i >= 0)
                        castedQuestion.content.choices[i] = s
                },
                deleteOnClickListener = {
                    deleteChoice?.invoke(it)
                }
            )
            else -> null
        }

        deleteChoice =  {
//            val removedIndex = castedQuestion.content.choices.indexOf(it)
            if (it < castedQuestion.content.choices.count() && it >= 0) {
                castedQuestion.content.choices.removeAt(it)
                adapter?.notifyDataSetChanged()
            }
        }

        val layoutManager = LinearLayoutManager(context)

        choicesRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        addChoiceButton.setOnClickListener {
            castedQuestion.content.choices.add("")
            adapter?.notifyItemInserted(castedQuestion.content.choices.count() - 1)
        }
    }
}