package com.kaisalar.android_client.data.model

class TextQuestionForCreation private constructor(title: String, description: String) :
    QuestionForCreation(title, description, QUESTION_TEXT) {

    companion object {
        fun getInstance() : TextQuestionForCreation {
            return TextQuestionForCreation(title = "", description = "")
        }
    }

    var content: Content

    init {
        this.content = Content(
            placeHolder = "",
            inputType = INPUT_TEXT,
            min = 0,
            max = 512
        )
    }

    inner class Content(
        var placeHolder: String,
        var inputType: String,
        var min: Int,
        var max: Int
    )
}