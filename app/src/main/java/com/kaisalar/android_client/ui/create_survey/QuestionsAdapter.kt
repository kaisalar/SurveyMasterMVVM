package com.kaisalar.android_client.ui.create_survey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*

class QuestionsAdapter(
    val context: Context,
    private val questions: List<QuestionForCreation>,
    private val deleteOnClickListener: (QuestionForCreation) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_TEXT = 0
        const val VIEW_TYPE_PARAGRAPH = 1
        const val VIEW_TYPE_RADIO_GROUP = 2
        const val VIEW_TYPE_CHECK_BOX = 3
        const val VIEW_TYPE_DROP_DOWN = 4
        const val VIEW_TYPE_ELSE = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val itemView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.question_text_list_item, parent, false)
                BasicTextQuestionHolder(itemView, deleteOnClickListener)
            }
            VIEW_TYPE_PARAGRAPH -> {
                val itemView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.question_paragraph_list_item, parent, false)
                BasicTextQuestionHolder(itemView, deleteOnClickListener)
            }
            VIEW_TYPE_RADIO_GROUP -> {
                val itemView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.question_radio_group_list_item, parent, false)
                MultipleChoiceQuestionHolder(context, itemView, deleteOnClickListener)
            }
            VIEW_TYPE_CHECK_BOX -> {
                val itemView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.question_check_box_list_item, parent, false)
                MultipleChoiceQuestionHolder(context, itemView, deleteOnClickListener)
            }
            VIEW_TYPE_DROP_DOWN -> {
                val itemView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.question_drop_down_list_item, parent, false)
                MultipleChoiceQuestionHolder(context, itemView, deleteOnClickListener)
            }
            else -> object : RecyclerView.ViewHolder(View(context)) {}
        }
    }

    override fun getItemCount(): Int {
        return questions.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_TEXT -> {
                val textHolder = holder as BasicTextQuestionHolder
                textHolder.bindQuestion(questions[position])
            }
            VIEW_TYPE_PARAGRAPH -> {
                val textHolder = holder as BasicTextQuestionHolder
                textHolder.bindQuestion(questions[position])
            }
            VIEW_TYPE_RADIO_GROUP, VIEW_TYPE_CHECK_BOX, VIEW_TYPE_DROP_DOWN -> {
                val multiHolder = holder as MultipleChoiceQuestionHolder
                multiHolder.bindQuestion(questions[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (questions[position].type) {
            QUESTION_TEXT -> VIEW_TYPE_TEXT
            QUESTION_PARAGRAPH -> VIEW_TYPE_PARAGRAPH
            QUESTION_RADIO_GROUP -> VIEW_TYPE_RADIO_GROUP
            QUESTION_CHECKBOX -> VIEW_TYPE_CHECK_BOX
            QUESTION_DROPDOWN -> VIEW_TYPE_DROP_DOWN
            else -> VIEW_TYPE_ELSE
        }
    }
}