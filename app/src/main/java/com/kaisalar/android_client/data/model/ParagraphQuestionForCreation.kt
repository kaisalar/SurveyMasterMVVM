package com.kaisalar.android_client.data.model

class ParagraphQuestionForCreation private constructor(title: String, description: String) :
    QuestionForCreation(title, description, QUESTION_PARAGRAPH) {

    companion object {
        fun getInstance() : ParagraphQuestionForCreation {
            return ParagraphQuestionForCreation(title = "", description = "")
        }
    }

    var content: Content

    init {
        this.content = Content(
            placeHolder = "",
            min = 0,
            max = 1024
        )
    }

    inner class Content(
        var placeHolder: String,
        var min: Int,
        var max: Int
    )
}