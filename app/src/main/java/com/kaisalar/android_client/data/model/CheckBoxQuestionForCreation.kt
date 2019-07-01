package com.kaisalar.android_client.data.model

class CheckBoxQuestionForCreation private constructor(title: String, description: String) :
    MultipleChoiceQuestion(title, description, QUESTION_CHECKBOX) {

    companion object {
        fun getInstance() : CheckBoxQuestionForCreation {
            return CheckBoxQuestionForCreation(title = "", description = "")
        }
    }
}