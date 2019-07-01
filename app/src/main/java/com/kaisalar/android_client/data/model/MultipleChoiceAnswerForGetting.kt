package com.kaisalar.android_client.data.model

class MultipleChoiceAnswerContent(val choices: List<String>)

class MultipleChoiceAnswerForGetting(
    type: String,
    question: QuestionForGetting,
    val content: MultipleChoiceAnswerContent
) : AnswerForGetting(type, question)