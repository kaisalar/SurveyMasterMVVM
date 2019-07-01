package com.kaisalar.android_client.data.model

class SurveyForCreation {
    var title: String
    var description: String
    var pages = mutableListOf<PageForCreation>()

    constructor(title: String, description: String) {
        this.title = title
        this.description = description
        this.pages.add(PageForCreation("", ""))
    }

    fun addQuestion(question: QuestionForCreation) {
        this.pages[0].addQuestion(question)
    }

    fun deleteQuestion(question: QuestionForCreation) {
        this.pages[0].deleteQuestion(question)
    }

    fun getQuestions() = pages[0].questions
}