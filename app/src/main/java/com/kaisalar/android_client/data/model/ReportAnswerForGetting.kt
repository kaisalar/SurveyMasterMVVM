package com.kaisalar.android_client.data.model

class ReportAnswerForGetting(
    val _id: String,
    val type: String,
    val title: String,
    val description: String,
    val content: HashMap<String, Int>
) {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("${this.title}:\n")
        when (this.type) {
            QUESTION_TEXT, QUESTION_PARAGRAPH -> {
                builder.append("The answers are:\n")
                for (key in content.keys) {
                    val str = "- $key (Repeated ${content[key]} time(s)).\n"
                    builder.append(str)
                }
            }
            QUESTION_RADIO_GROUP, QUESTION_CHECKBOX, QUESTION_DROPDOWN -> {
                builder.append("The percentages are:\n")
                var total = 0
                for (key in content.keys) {
                    total += content[key] ?: 0
                }
                for (key in content.keys) {
                    val percentage = calculatePercentage(content[key] ?: 0, total)
                    val str = "- $key ($percentage%)\n"
                    builder.append(str)
                }
            }
            QUESTION_SLIDER, QUESTION_RATING -> {
                builder.append("The answers are:\n")
                for (key in content.keys) {
                    val str = "- $key (Repeated ${content[key]} time(s)).\n"
                    builder.append(str)
                }
            }
            QUESTION_RANGE -> {
                builder.append("The answers are:\n")
                for (key in content.keys) {
                    val str = "- $key (Repeated ${content[key]} time(s)).\n"
                    builder.append(str)
                }
            }
        }
        return builder.toString()
    }

    private fun calculatePercentage(value: Int, total: Int): Double {
        return value * 100 / total * 1.0
    }
}