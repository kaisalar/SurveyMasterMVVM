package com.kaisalar.android_client.util

object StringValidationUtils {
    fun isEmail(string: String): Boolean {
        val pattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$".toRegex()
        return pattern.matches(string)
    }

    fun isBlankOrEmpty(string: String): Boolean {
        return string.isEmpty() || string.isBlank()
    }
}