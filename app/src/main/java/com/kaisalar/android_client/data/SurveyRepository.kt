package com.kaisalar.android_client.data

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.kaisalar.android_client.data.db.dao.SurveyDao
import com.kaisalar.android_client.data.db.entity.SurveyEntity
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.data.webservice.SurveysService

class SurveyRepository(context: Context, private val surveyDao: SurveyDao) {
    companion object {
        @Volatile
        private var INSTANCE: SurveyRepository? = null
        fun getInstance(context: Context, surveyDao: SurveyDao) = synchronized(this) {
            INSTANCE ?: SurveyRepository(context, surveyDao).also { INSTANCE = it }
        }
    }

    private val surveysService: SurveysService by lazy {
        SurveysService.getInstance(context)
    }

    fun getSurveys(): LiveData<List<SurveyEntity>> {
        return surveyDao.getAllSurveys()
    }

    fun getSurveyResponses(
        surveyId: String,
        onSuccess: (List<ResponseForGetting>) -> Unit,
        onFailure: () -> Unit
    ) {
        surveysService.getSurveyResponses(surveyId, onSuccess, onFailure)
    }

    fun getSurveyResponseAnswers(
        surveyId: String,
        responseId: String,
        onSuccess: (List<AnswerForGetting>) -> Unit,
        onFailure: () -> Unit
    ) {
        surveysService.getSurveyResponseAnswers(surveyId, responseId, onSuccess, onFailure)
    }

    fun getSurveyReport(surveyId: String, onSuccess: (ReportForGetting) -> Unit, onFailure: () -> Unit) {
        surveysService.getSurveyReport(
            surveyId = surveyId,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun getSurveyUsers(surveyId: String, onSuccess: (List<UserForSurvey>) -> Unit, onFailure: () -> Unit) {
        surveysService.getSurveyUsers(
            surveyId = surveyId,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun addUserForSurvey(user: UserForSurveyForCreation, surveyId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveysService.addUserForSurvey(
            user = user,
            surveyId = surveyId,
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun deleteUserFromSurvey(userId: String, surveyId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveysService.deleteUserFromSurvey(
            userId = userId,
            surveyId = surveyId,
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun updateSurveys(onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveysService.getSurveys(
            onSuccess = {
                class UpdateSurveys : AsyncTask<Unit, Unit, Unit>() {
                    override fun doInBackground(vararg params: Unit?) {
                        surveyDao.clear()
                        surveyDao.insertAll(convertModelListToEntities(it))
                    }

                    override fun onPostExecute(result: Unit?) {
                        onSuccess()
                    }
                }
                UpdateSurveys().execute()
            },
            onFailure = {
                onFailure()
            }
        )
    }

    fun addSurvey(survey: SurveyForCreation, onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveysService.addSurvey(survey, onSuccess, onFailure)
    }

    fun deleteSurvey(surveyId: String) {
        class DeleteSurvey : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                surveyDao.delete(surveyId)
            }
        }
        DeleteSurvey().execute()
    }

    private fun convertModelToEntity(surveyForGetting: SurveyForGetting): SurveyEntity {
        return SurveyEntity(
            _id = surveyForGetting._id,
            title = surveyForGetting.title,
            description = surveyForGetting.description,
            link = surveyForGetting.link,
            date = surveyForGetting.date
        )
    }

    private fun convertModelListToEntities(list: List<SurveyForGetting>): List<SurveyEntity> {
        val newList = mutableListOf<SurveyEntity>()
        for(s in list) newList.add(convertModelToEntity(s))
        return newList
    }

    fun clearData() {
        class ClearSurveys : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                surveyDao.clear()
            }
        }
        ClearSurveys().execute()
    }
}