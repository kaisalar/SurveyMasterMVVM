package com.kaisalar.android_client.ui.create_survey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kaisalar.android_client.R
import kotlinx.android.synthetic.main.activity_main.*

class CreateSurveyActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_survey)

        navController = findNavController(R.id.create_survey_nav_host)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.surveyBasicInfoFragment,
                R.id.surveyQuestionsFragment,
                R.id.selectQuestionDialog,
                R.id.submitSurveyFragment,
                R.id.loadingDialog
            )
        )
        createSurveyToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
