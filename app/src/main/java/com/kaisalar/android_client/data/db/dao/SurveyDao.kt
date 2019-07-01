package com.kaisalar.android_client.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaisalar.android_client.data.db.entity.SurveyEntity

@Dao
interface SurveyDao {
    @Query(value = "SELECT * FROM surveys_info ORDER BY date DESC")
    fun getAllSurveys() : LiveData<List<SurveyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(surveys: List<SurveyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(survey: SurveyEntity)

    @Query(value = "DELETE FROM surveys_info")
    fun clear()

    @Query(value = "DELETE FROM surveys_info WHERE _id = :surveyId")
    fun delete(surveyId: String)
}