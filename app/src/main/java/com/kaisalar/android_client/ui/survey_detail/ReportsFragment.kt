package com.kaisalar.android_client.ui.survey_detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kaisalar.android_client.R
import kotlinx.android.synthetic.main.reports_fragment.*

class ReportsFragment : Fragment() {

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private lateinit var viewModel: SurveyDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reports_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)
        }

        goToReports.setOnClickListener {
            val intent = Intent(context, ReportsActivity::class.java)
            intent.putExtra(EXTRA_SURVEY_ID, viewModel.surveyId)
            startActivity(intent)
        }
    }
}
