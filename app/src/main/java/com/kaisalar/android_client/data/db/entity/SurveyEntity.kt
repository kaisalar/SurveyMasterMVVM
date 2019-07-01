package com.kaisalar.android_client.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surveys_info")
data class SurveyEntity(
    @PrimaryKey val _id: String,
    val title: String,
    val description: String,
    val link: String,
    val date: Long
)