package com.kaisalar.android_client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.data.webservice.SurveysService
import com.kaisalar.android_client.util.StringValidationUtils
import java.util.*

class CreateSurveyViewModel(application: Application) : AndroidViewModel(application) {
    private val surveyRepo: SurveyRepository
    private val createdSurvey = MutableLiveData<SurveyForCreation>()

    init {
        val surveyDao = ApplicationDatabase.getInstance(getApplication()).surveyDao()
        surveyRepo = SurveyRepository.getInstance(getApplication(), surveyDao)
        createdSurvey.postValue(SurveyForCreation("", ""))
    }

    fun addQuestion(question: QuestionForCreation) {
        val newSurvey = createdSurvey.value
        newSurvey?.addQuestion(question)
        createdSurvey.postValue(newSurvey)
    }

    fun deleteQuestion(question: QuestionForCreation) {
        val newSurvey = createdSurvey.value
        newSurvey?.deleteQuestion(question)
        createdSurvey.postValue(newSurvey)
    }

    fun swapQuestions(p1: Int, p2:Int) {
        Collections.swap(createdSurvey.value?.getQuestions(), p1, p2)
        createdSurvey.postValue(createdSurvey.value)
    }

    fun postTitle(title: String) {
        createdSurvey.value?.title = title
    }

    fun postDescription(description: String) {
        createdSurvey.value?.description = description
    }

    fun postColor(color: String) {
        createdSurvey.value?.color = color
    }

    fun getCreatedSurvey() = createdSurvey as LiveData<SurveyForCreation>

    fun isValidQuestionData(): Boolean {
        for (q in getCreatedSurvey().value?.getQuestions()?.iterator()!!) {
            if (StringValidationUtils.isBlankOrEmpty(q.title)) return false
            if (q.type == QUESTION_RADIO_GROUP || q.type == QUESTION_CHECKBOX || q.type == QUESTION_DROPDOWN) {
                val mcq = q as MultipleChoiceQuestion
                if (mcq.content.choices.count() == 0) return false
                for (c in mcq.content.choices) {
                    if (StringValidationUtils.isBlankOrEmpty(c)) return false
                }
            }
        }
        return true
    }

    fun addSurvey(onSuccess: () -> Unit, onFailure: () -> Unit) {
        surveyRepo.addSurvey(createdSurvey.value!!, onSuccess, onFailure)
    }

    fun cancelCreateRequest() {
        SurveysService.getInstance(getApplication()).cancelAddSurveyRequest()
    }
}