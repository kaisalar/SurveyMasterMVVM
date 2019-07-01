package com.kaisalar.android_client.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaisalar.android_client.data.db.dao.SurveyDao
import com.kaisalar.android_client.data.db.entity.SurveyEntity

@Database(entities = [SurveyEntity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object{
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null
        fun getInstance(context: Context) = synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context, ApplicationDatabase::class.java, "app_database"
            ).build().also { INSTANCE = it }
        }
    }

    fun clear() {
        surveyDao().clear()
    }
}