package com.kaisalar.android_client.ui.create_survey

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.QuestionForCreation

open class ExpandableQuestionHolder(itemView: View, deleteOnClickListener: (QuestionForCreation) -> Unit) :
    QuestionHolder(itemView, deleteOnClickListener) {

    private val advanceSettingsButton = itemView.findViewById<MaterialButton>(R.id.addChoiceButton)
    private val advanceSettingsLayout = itemView.findViewById<ConstraintLayout>(R.id.choicesRecyclerView)

    override fun bindQuestion(question: QuestionForCreation) {
        super.bindQuestion(question)

        advanceSettingsLayout.visibility = View.GONE
        advanceSettingsButton.text = "SHOW ADVANCED"

        advanceSettingsButton.setOnClickListener {
            when (advanceSettingsLayout.visibility) {
                View.GONE -> {
                    advanceSettingsLayout.visibility = View.VISIBLE
                    advanceSettingsButton.text = "HIDE ADVANCED"
                }
                View.VISIBLE -> {
                    advanceSettingsLayout.visibility = View.GONE
                    advanceSettingsButton.text = "SHOW ADVANCED"
                }
            }

        }
    }
}