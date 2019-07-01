package com.kaisalar.android_client.ui.create_survey

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*

open class BasicTextQuestionHolder(itemView: View, deleteOnClickListener: (QuestionForCreation) -> Unit) :
    ExpandableQuestionHolder(itemView, deleteOnClickListener) {

    private val placeHolderTextLayout = itemView.findViewById<TextInputLayout>(R.id.placeHolderTextLayout)
    private val placeHolderEditText = itemView.findViewById<EditText>(R.id.placeHolderEditText)

    private val minTextLayout = itemView.findViewById<TextInputLayout>(R.id.minimumTextLayout)
    private val minEditText = itemView.findViewById<EditText>(R.id.minimumEditText)

    private val maxTextLayout = itemView.findViewById<TextInputLayout>(R.id.maximumTextLayout)
    private val maxEditText = itemView.findViewById<EditText>(R.id.maximumEditText)

    override fun bindQuestion(question: QuestionForCreation) {
        super.bindQuestion(question)

        when (question.type) {
            QUESTION_TEXT ->  {
                val castedQuestion = question as TextQuestionForCreation
                placeHolderEditText.setText(castedQuestion.content.placeHolder)
                minEditText.setText(castedQuestion.content.min.toString())
                maxEditText.setText(castedQuestion.content.max.toString())

                placeHolderEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        castedQuestion.content.placeHolder = s.toString()
                    }
                })

                minEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        try {
                            castedQuestion.content.min = s.toString().toInt()
                        } catch (e: Exception) {

                        }
                    }
                })

                maxEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        try {
                            castedQuestion.content.max = s.toString().toInt()
                        } catch (e: Exception) {

                        }

                    }
                })
            }

            QUESTION_PARAGRAPH -> {
                val castedQuestion = question as ParagraphQuestionForCreation
                placeHolderEditText.setText(castedQuestion.content.placeHolder)
                minEditText.setText(castedQuestion.content.min.toString())
                maxEditText.setText(castedQuestion.content.max.toString())

                placeHolderEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        castedQuestion.content.placeHolder = s.toString()
                    }
                })

                minEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        castedQuestion.content.min = s.toString().toInt()
                    }
                })

                maxEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        castedQuestion.content.max = s.toString().toInt()
                    }
                })
            }
        }
    }
}