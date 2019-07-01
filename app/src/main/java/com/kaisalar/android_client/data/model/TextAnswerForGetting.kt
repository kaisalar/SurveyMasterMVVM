package com.kaisalar.android_client.data.model

class TextAnswerContent(val value: String)

class TextAnswerForGetting(type: String, question : QuestionForGetting, val content: TextAnswerContent) :
    AnswerForGetting(type, question)