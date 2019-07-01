package com.kaisalar.android_client.data.model

open class MultipleChoiceQuestion(title: String, description: String, type: String) :
    QuestionForCreation(title, description, type) {

    var content = Content()

    inner class Content {
        var choices = mutableListOf<String>()
    }
}