package com.kaisalar.android_client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.db.entity.SurveyEntity
import com.kaisalar.android_client.data.webservice.SurveysService

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

    fun cancelGetSurveysRequest() {
        SurveysService.getInstance(getApplication()).cancelGetAllSurveysRequest()
    }

    fun cancelDeleteSurveyRequest() {
        SurveysService.getInstance(getApplication()).cancelDeleteSurveyRequest()
    }

    fun cancelAllHttpRequests() {
        cancelGetSurveysRequest()
        cancelDeleteSurveyRequest()
    }
}