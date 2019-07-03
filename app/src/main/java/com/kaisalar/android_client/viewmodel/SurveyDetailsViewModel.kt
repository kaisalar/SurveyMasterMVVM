package com.kaisalar.android_client.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.data.webservice.SurveysService

class SurveyDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val surveyRepo: SurveyRepository
    var surveyId: String = ""
    val users = MutableLiveData<List<UserForSurvey>>()

    init {
        val surveyDao = ApplicationDatabase.getInstance(getApplication()).surveyDao()
        surveyRepo = SurveyRepository.getInstance(getApplication(), surveyDao)
        users.postValue(mutableListOf())
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

    fun getSurveyUsers(onSuccess: (List<UserForSurvey>) -> Unit, onFailure: () -> Unit) {
        surveyRepo.getSurveyUsers(
            surveyId = surveyId,
            onSuccess = {
                users.postValue(it)
                onSuccess(it)
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun addUserForSurvey(user: UserForSurveyForCreation,onSuccess: () -> Unit,  onFailure: () -> Unit) {
        surveyRepo.addUserForSurvey(
            user = user,
            surveyId =  surveyId,
            onSuccess = {
                class GetUsers : AsyncTask<Unit, Unit, Unit>() {
                    override fun doInBackground(vararg params: Unit?) {
                        getSurveyUsers(
                            onSuccess = {
                                users.postValue(it)
                            },
                            onFailure = {}
                        )
                    }
                }
                GetUsers().execute()
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun deleteUserFromSurvey(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        if (userId.isEmpty()) return
        surveyRepo.deleteUserFromSurvey(
            userId = userId,
            surveyId = surveyId,
            onSuccess = {
                class GetUsers : AsyncTask<Unit, Unit, Unit>() {
                    override fun doInBackground(vararg params: Unit?) {
                        getSurveyUsers(
                            onSuccess = {
                                users.postValue(it)
                            },
                            onFailure = {}
                        )
                    }
                }
                GetUsers().execute()
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun cancelGetResponsesRequest() {
        SurveysService.getInstance(getApplication()).cancelGetSurveyResponsesRequest()
    }

    fun cancelGetResponseAnswersRequest() {
        SurveysService.getInstance(getApplication()).cancelGetSurveyResponseAnswers()
    }

    fun cancelGetSurveyReportRequest() {
        SurveysService.getInstance(getApplication()).cancelGetSurveyReport()
    }

    fun cancelGetSurveyUsersRequest() {
        SurveysService.getInstance(getApplication()).cancelGetSurveyUsersRequest()
    }

    fun cancelAddUserForSurveyRequest() {
        SurveysService.getInstance(getApplication()).cancelAddUserForSurveyRequest()
    }

    fun cancelDeleteUserForSurveyRequest() {
        SurveysService.getInstance(getApplication()).cancelDeleteUserForSurveyRequest()
    }
}