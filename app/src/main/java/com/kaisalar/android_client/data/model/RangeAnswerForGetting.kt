package com.kaisalar.android_client.data.model

class RangeAnswerContent(val minValue: Number, val maxValue: Number)

class RangeAnswerForGetting(
    type: String,
    question: QuestionForGetting,
    val content: RangeAnswerContent
) : AnswerForGetting(type, question)