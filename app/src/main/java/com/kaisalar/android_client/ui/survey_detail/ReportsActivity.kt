package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
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
        supportFragmentManager.beginTransaction()
            .add(R.id.reportChartContiner, SettingsFragment())
            .commit()

        var i = 0
        decideChart(report.answers[i])

        reportNextButton.setOnClickListener {
            if(i < report.answers.size - 1) {
                i++
                decideChart(report.answers[i])
                Toast.makeText(baseContext, report.answers[i].type, Toast.LENGTH_LONG).show()
            }
        }

        reportPreviuosButton.setOnClickListener {
            if(i > 0) {
                i--
                decideChart(report.answers[i])
                Toast.makeText(baseContext, report.answers[i].type, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun decideChart(answer: ReportAnswerForGetting){
        when (answer.type) {
            QUESTION_TEXT, QUESTION_PARAGRAPH -> buildBarChart(answer)
            QUESTION_CHECKBOX, QUESTION_RADIO_GROUP, QUESTION_DROPDOWN -> buildPieChart(answer)
            QUESTION_SLIDER, QUESTION_RATING -> buildLineChart(answer, false)
            QUESTION_RANGE -> buildLineChart(answer, true)
            else -> {
                buildBarChart(answer)
            }
        }
    }

    private fun buildBarChart(answer: ReportAnswerForGetting) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(BarChartFragment.newInstance(questionTitle, labels, values), true)
    }

    private fun buildPieChart(answer: ReportAnswerForGetting) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(PieChartFragment.newInstance(questionTitle, labels, values), true)
    }

    private fun buildLineChart(answer: ReportAnswerForGetting, isRange: Boolean) {
        val questionTitle = answer.title
        val labels = ArrayList<String>()
        labels.addAll(answer.content.keys)
        val values = ArrayList<Int>()
        values.addAll(answer.content.values)
        showFragment(LineChartFragment.newInstance(questionTitle, labels, values, isRange), true)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val mFragment = supportFragmentManager.beginTransaction()
        mFragment.replace(R.id.reportChartContiner, fragment)
        if (addToBackStack) mFragment.addToBackStack(fragment.toString())
        mFragment.commit()
    }
}
