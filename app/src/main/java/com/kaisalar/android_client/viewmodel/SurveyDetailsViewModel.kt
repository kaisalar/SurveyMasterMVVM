package com.kaisalar.android_client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.model.AnswerForGetting
import com.kaisalar.android_client.data.model.ReportForGetting
import com.kaisalar.android_client.data.model.ResponseForGetting

class SurveyDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val surveyRepo: SurveyRepository
    var surveyId: String = ""

    init {
        val surveyDao = ApplicationDatabase.getInstance(getApplication()).surveyDao()
        surveyRepo = SurveyRepository.getInstance(getApplication(), surveyDao)
    }

    fun getSurveyResponses(onSuccess: (List<ResponseForGetting>) -> Unit, onFailure: () -> Unit) {
        surveyRepo.getSurveyResponses(surveyId, onSuccess, onFailure)
    }

    fun getSurveyResponseAnswers(
        responseId: String,
        onSuccess: (List<AnswerForGetting>) -> Unit,
        onFailure: () -> Unit
    ) {
        surveyRepo.getSurveyResponseAnswers(surveyId, responseId, onSuccess, onFailure)
    }

    fun getSurveyReport(onSuccess: (ReportForGetting) -> Unit, onFailure: () -> Unit) {
        surveyRepo.getSurveyReport(
            surveyId = surveyId,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure()
            }
        )
    }
}