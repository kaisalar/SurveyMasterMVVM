package com.kaisalar.android_client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.model.*
import com.kaisalar.android_client.util.StringValidationUtils

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

    fun postTitle(title: String) {
        createdSurvey.value?.title = title
    }

    fun postDescription(description: String) {
        createdSurvey.value?.description = description
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
}