package com.kaisalar.android_client.data.model

data class UserForCreation(var firstName: String, var lastName: String, var email: String, var password: String) {
    override fun toString(): String {
        return "$firstName $lastName, $email, $password"
    }
}