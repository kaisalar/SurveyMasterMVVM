package com.kaisalar.android_client.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun convertTimeMillis(millis: Long) : String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }
}