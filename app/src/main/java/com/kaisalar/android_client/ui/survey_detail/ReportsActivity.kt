package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kaisalar.android_client.R
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.data.webservice.SurveysService
import com.kaisalar.android_client.ui.main.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_reports.*

class ReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        reportPreviuosButton.visibility = View.INVISIBLE
        val surveyId = intent.getStringExtra(EXTRA_SURVEY_ID)

        SurveysService.getInstance(this).getSurveyReport(
            surveyId = surveyId,
            onSuccess = {
                setUpCharts(it)
            },
            onFailure = {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        )
    }

    private fun setUpCharts(report: ReportForGetting) {

        reportPreviuosButton.visibility = View.INVISIBLE
        supportFragmentManager.beginTransaction()
            .add(R.id.reportChartContiner, SettingsFragment())
            .commit()

        var i = 0
        decideChart(report.answers, i)

        reportNextButton.setOnClickListener {
            if(i < report.answers.size - 1) {
                i++
                decideChart(report.answers, i)
            }
        }

        reportPreviuosButton.setOnClickListener {
            if(i > 0) {
                i--
                decideChart(report.answers, i)
            }
        }
    }

    private fun decideChart(answers: List<ReportAnswerForGetting>, position: Int){
        reportNextButton.visibility = View.VISIBLE
        reportPreviuosButton.visibility = View.VISIBLE
        if (position == 0){
            reportPreviuosButton.visibility = View.INVISIBLE
        }
        if (position == answers.count() - 1) {
            reportNextButton.visibility = View.INVISIBLE
        }
        when (answers[position].type) {
            QUESTION_TEXT, QUESTION_PARAGRAPH -> buildBarChart(answers[position])
            QUESTION_CHECKBOX, QUESTION_RADIO_GROUP, QUESTION_DROPDOWN -> buildPieChart(answers[position])
            QUESTION_SLIDER, QUESTION_RATING -> buildLineChart(answers[position], false)
            QUESTION_RANGE -> buildLineChart(answers[position], true)
            else -> {
                buildBarChart(answers[position])
            }
        }
    }

    private fun buildBarChart(answer: ReportAnswerForGetting) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(BarChartFragment.newInstance(questionTitle, labels, values), false)
    }

    private fun buildPieChart(answer: ReportAnswerForGetting) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(PieChartFragment.newInstance(questionTitle, labels, values), false)
    }

    private fun buildLineChart(answer: ReportAnswerForGetting, isRange: Boolean) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(LineChartFragment.newInstance(questionTitle, labels, values, isRange), false)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val mFragment = supportFragmentManager.beginTransaction()
        mFragment.replace(R.id.reportChartContiner, fragment)
        if (addToBackStack) mFragment.addToBackStack(fragment.toString())
        mFragment.commit()
    }

    override fun onStop() {
        super.onStop()
        SurveysService.getInstance(this).cancelGetSurveyReport()
    }
}
