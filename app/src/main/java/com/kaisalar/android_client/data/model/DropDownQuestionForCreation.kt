package com.kaisalar.android_client.data.model

class DropDownQuestionForCreation private constructor(title: String, description: String) :
    MultipleChoiceQuestion(title, description, QUESTION_DROPDOWN) {

    companion object {
        fun getInstance() : DropDownQuestionForCreation {
            return DropDownQuestionForCreation(title = "", description = "")
        }
    }
}