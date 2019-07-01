package com.kaisalar.android_client.data.model

class SingleNumberValueAnswerContent(val value: Number)

class SingleNumberValueAnswerForGetting(
    type: String,
    question: QuestionForGetting,
    val content: SingleNumberValueAnswerContent
) : AnswerForGetting(type, question)