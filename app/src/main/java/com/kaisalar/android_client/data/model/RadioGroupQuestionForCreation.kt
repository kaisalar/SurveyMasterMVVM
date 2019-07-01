package com.kaisalar.android_client.data.model

class RadioGroupQuestionForCreation private constructor(title: String, description: String) :
    MultipleChoiceQuestion(title, description, QUESTION_RADIO_GROUP) {

    companion object {
        fun getInstance() : RadioGroupQuestionForCreation {
            return RadioGroupQuestionForCreation(title = "", description = "")
        }
    }
}