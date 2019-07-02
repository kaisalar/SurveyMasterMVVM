package com.kaisalar.android_client.ui.create_survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.viewmodel.CreateSurveyViewModel

class SelectQuestionDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: CreateSurveyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(CreateSurveyViewModel::class.java)
        }

        val text = view.findViewById<MaterialButton>(R.id.questionTextButton)
        text.setOnClickListener {
            viewModel.addQuestion(TextQuestionForCreation.getInstance())
            findNavController().navigateUp()
        }

        val paragraph = view.findViewById<MaterialButton>(R.id.questionParagraphButton)
        paragraph.setOnClickListener {
            viewModel.addQuestion(ParagraphQuestionForCreation.getInstance())
            findNavController().navigateUp()
        }

        val radio = view.findViewById<MaterialButton>(R.id.questionRadioGroupButton)
        radio.setOnClickListener {
            viewModel.addQuestion(RadioGroupQuestionForCreation.getInstance())
            findNavController().navigateUp()
        }

        val check = view.findViewById<MaterialButton>(R.id.questionCheckBoxButton)
        check.setOnClickListener {
            viewModel.addQuestion(CheckBoxQuestionForCreation.getInstance())
            findNavController().navigateUp()
        }

        val drop = view.findViewById<MaterialButton>(R.id.questionDropDownButton)
        drop.setOnClickListener {
            viewModel.addQuestion(DropDownQuestionForCreation.getInstance())
            findNavController().navigateUp()
        }
    }
}