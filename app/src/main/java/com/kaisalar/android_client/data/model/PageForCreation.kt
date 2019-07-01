package com.kaisalar.android_client.data.model

class PageForCreation {
    var title: String
    var description: String
    var questions = mutableListOf<QuestionForCreation>()

    constructor(title: String, description: String) {
        this.title = title
        this.description = description
    }

    fun addQuestion(question: QuestionForCreation) {
        this.questions.add((question))
    }

    fun deleteQuestion(question: QuestionForCreation) {
        this.questions.remove(question)
    }
}