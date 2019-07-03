package com.kaisalar.android_client.ui.survey_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kaisalar.android_client.R
import com.kaisalar.android_client.viewmodel.SurveyDetailsViewModel
import kotlinx.android.synthetic.main.activity_survey_details.*

class SurveyDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: SurveyDetailsViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_details)

        viewModel = ViewModelProviders.of(this).get(SurveyDetailsViewModel::class.java)

        val surveyId = intent.getStringExtra(EXTRA_SURVEY_ID)
        viewModel.surveyId = surveyId

        navController = findNavController(R.id.surveyDetailsNavHost)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.responsesFragment, R.id.reportsFragment, R.id.answersDialog, R.id.usersFragment)
        )
        surveyDetailsBottomNavigationView.setupWithNavController(navController)
        surveyDetailsToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
