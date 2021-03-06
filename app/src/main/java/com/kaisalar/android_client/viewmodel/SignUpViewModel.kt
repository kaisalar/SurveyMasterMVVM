package com.kaisalar.android_client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaisalar.android_client.data.model.UserForCreation
import com.kaisalar.android_client.data.webservice.AuthService

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val creationUser = MutableLiveData<UserForCreation>()
    init {
        creationUser.postValue(UserForCreation("", "", "", ""))
    }

    fun getCreationUser() = creationUser

    fun setUserFirstName(firstName: String) {
        getCreationUser().value?.firstName = firstName
    }

    fun setUserLastName(lastName: String) {
        getCreationUser().value?.lastName = lastName
    }

    fun setUserEmail(email: String) {
        getCreationUser().value?.email = email
    }

    fun setUserPassword(password: String) {
        getCreationUser().value?.password = password
    }

    fun createAccount(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        AuthService.getInstance(getApplication()).createAccount(getCreationUser().value!!, onSuccess, onFailure)
    }
}
