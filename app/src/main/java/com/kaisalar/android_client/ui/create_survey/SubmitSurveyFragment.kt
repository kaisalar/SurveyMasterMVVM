package com.kaisalar.android_client.ui.create_survey

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.ui.main.MainActivity
import com.kaisalar.android_client.viewmodel.CreateSurveyViewModel
import kotlinx.android.synthetic.main.submit_survey_fragment.*

class SubmitSurveyFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitSurveyFragment()
    }

    private lateinit var viewModel: CreateSurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.submit_survey_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(CreateSurveyViewModel::class.java)
        }

        viewModel.getCreatedSurvey().observe(this, Observer {
            titleTextView.text = it.title
            descriptionTextView.text = it.description
            questionsNumberTextView.text = "You have ${it.getQuestions().count()} question(s)"
            val map = HashMap<String, Int>()
            for (q in it.getQuestions()) {
                if (map.containsKey(q.type)) {
                    val newValue = map[q.type]!! + 1
                    map[q.type] = newValue
                } else {
                    map[q.type] = 1
                }
            }
            val strBuilder = StringBuilder()
            for (key in map.keys) {
                when (key) {
                    QUESTION_TEXT -> strBuilder.append("${map[key]} Text Question\n")
                    QUESTION_PARAGRAPH -> strBuilder.append("${map[key]} Paragraph Question\n")
                    QUESTION_RADIO_GROUP -> strBuilder.append("${map[key]} Radio Group Question\n")
                    QUESTION_CHECKBOX -> strBuilder.append("${map[key]} Check Box Question\n")
                    QUESTION_DROPDOWN -> strBuilder.append("${map[key]} Drop Down Question\n")
                }
            }
            questionsTextView.text = strBuilder.toString()
        })


        prevButton.setOnClickListener {
            findNavController().navigateUp()
        }

        submitButton.setOnClickListener {
            val action = SubmitSurveyFragmentDirections.loadingAction()
            findNavController().navigate(action)

            viewModel.addSurvey(
                onSuccess = {
                    findNavController().navigateUp()
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.run {
                        finish()
                    }
                },
                onFailure = {
                    findNavController().navigateUp()
                    Snackbar.make(submitButton, "Something went wrong!", Snackbar.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancelCreateRequest()
    }
}
