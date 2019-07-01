package com.kaisalar.android_client.data.model

class ReportForGetting(val surveyId: String, val answers: List<ReportAnswerForGetting>) {
    override fun toString(): String {
        val builder = StringBuilder()
        for (answer in answers) {
            builder.append(answer.toString())
            builder.append("**********\n")
        }
        return builder.toString()
    }
}