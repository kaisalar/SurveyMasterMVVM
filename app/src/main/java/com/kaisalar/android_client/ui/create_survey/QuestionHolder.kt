package com.kaisalar.android_client.ui.create_survey

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.QuestionForCreation

open class QuestionHolder(itemView: View, val deleteOnClickListener: (QuestionForCreation) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val titleTextLayout = itemView.findViewById<TextInputLayout>(R.id.titleTextLayout)
    private val titleEditText = itemView.findViewById<EditText>(R.id.titleEditText)

    private val descriptionTextLayout = itemView.findViewById<TextInputLayout>(R.id.descriptionTextLayout)
    private val descriptionEditText = itemView.findViewById<EditText>(R.id.descriptionEditText)

    private val deleteButton = itemView.findViewById<MaterialButton>(R.id.deleteButton)

    open fun bindQuestion(question: QuestionForCreation) {
        titleEditText.setText(question.title)
        descriptionEditText.setText(question.description)

        titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question.title = s.toString()
            }
        })

        descriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question.description = s.toString()
            }
        })

        deleteButton.setOnClickListener { deleteOnClickListener(question) }
    }
}