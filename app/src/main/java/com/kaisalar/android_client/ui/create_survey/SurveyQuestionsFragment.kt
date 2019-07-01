package com.kaisalar.android_client.ui.create_survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.QuestionForCreation
import kotlinx.android.synthetic.main.survey_questions_fragment.*

class SurveyQuestionsFragment : Fragment() {

    private lateinit var viewModel: CreateSurveyViewModel
    private lateinit var globalAdapter: QuestionsAdapter
    private val questions = mutableListOf<QuestionForCreation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.survey_questions_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(CreateSurveyViewModel::class.java)
        }

        viewModel.getCreatedSurvey().observe(this, Observer {
            questions.clear()
            questions.addAll(it.getQuestions())
            globalAdapter.notifyDataSetChanged()
        })

        val adapter = QuestionsAdapter(
            context = context!!,
            questions = questions,
            deleteOnClickListener = {
                deleteQuestion(it)
            }
        )

        globalAdapter = adapter

        val layoutManager = LinearLayoutManager(context)

        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        addQuestionButton.setOnClickListener {
            val action = SurveyQuestionsFragmentDirections.selectAction()
            findNavController().navigate(action)
        }

        nextButton.setOnClickListener {
            val res = viewModel.isValidQuestionData()
            if (res) {
                val action = SurveyQuestionsFragmentDirections.nextAction()
                findNavController().navigate(action)
            } else {
                Snackbar.make(
                    addQuestionButton,
                    "Check your questions data and try again :)",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        prevButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun deleteQuestion(question: QuestionForCreation) {
        val removedIndex = questions.indexOf(question)
        viewModel.deleteQuestion(question)
        globalAdapter.notifyItemRemoved(removedIndex)
    }
}
