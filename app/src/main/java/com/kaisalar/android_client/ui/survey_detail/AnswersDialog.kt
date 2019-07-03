package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.kaisalar.android_client.data.model.AnswerForGetting
import com.kaisalar.android_client.viewmodel.SurveyDetailsViewModel


class AnswersDialog : DialogFragment() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var loadingAnswersBar: ProgressBar
    private lateinit var answersRecyclerView: RecyclerView

    private lateinit var responseId: String
    private lateinit var viewModel: SurveyDetailsViewModel

    private val answers = mutableListOf<AnswerForGetting>()
    private lateinit var globalAdapter: AnswersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.kaisalar.android_client.R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(com.kaisalar.android_client.R.layout.dialog_response_answers, container, false)

        toolbar = view.findViewById(com.kaisalar.android_client.R.id.toolbar)
        loadingAnswersBar = view.findViewById(com.kaisalar.android_client.R.id.loadingAnswersBar)
        answersRecyclerView = view.findViewById(com.kaisalar.android_client.R.id.answersRecyclerView)

        toolbar.setNavigationIcon(com.kaisalar.android_client.R.drawable.ic_clear)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Response Answers"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)
        }

        arguments?.let {
            val safeArgs = AnswersDialogArgs.fromBundle(it)
            responseId = safeArgs.responseId
        }

        val adapter = AnswersAdapter(
            context = context!!,
            answers = answers
        )
        globalAdapter = adapter

        val layoutManager = LinearLayoutManager(context)

        answersRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        viewModel.getSurveyResponseAnswers(
            responseId = responseId,
            onSuccess = {
                answers.clear()
                answers.addAll(it)
                globalAdapter.notifyDataSetChanged()
                loadingAnswersBar.visibility = View.GONE
            },
            onFailure = {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        )
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }
}