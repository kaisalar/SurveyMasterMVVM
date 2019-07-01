package com.kaisalar.android_client.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kaisalar.android_client.data.SurveyRepository
import com.kaisalar.android_client.data.db.ApplicationDatabase
import com.kaisalar.android_client.data.webservice.AuthService

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    fun signOut() {
        val surveyDao = ApplicationDatabase.getInstance(getApplication()).surveyDao()
        SurveyRepository.getInstance(getApplication(), surveyDao).clearData()
        AuthService.getInstance(getApplication()).signOut()
    }
}
