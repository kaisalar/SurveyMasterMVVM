package com.kaisalar.android_client.ui.main.surveys

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.db.entity.SurveyEntity

class SurveysViewModel(application: Application): AndroidViewModel(application) {
    private val surveyRepo: SurveyRepository
    val surveys: LiveData<List<SurveyEntity>>
    var isInitUpdated = false

    init {
        val surveyDao = ApplicationDatabase.getInstance(getApplication()).surveyDao()
        surveyRepo = SurveyRepository.getInstance(getApplication(), surveyDao)
        surveys = surveyRepo.getSurveys()
    }

    fun updateSurveys(onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveyRepo.updateSurveys(onSuccess, onFailure)
    }

    fun deleteSurvey(surveyId: String) {
        surveyRepo.deleteSurvey(surveyId)
    }
}