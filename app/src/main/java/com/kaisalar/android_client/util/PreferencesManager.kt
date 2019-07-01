package com.kaisalar.android_client.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferencesManager(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: PreferencesManager? = null
        fun getInstance(context: Context) = synchronized(this) {
            INSTANCE
                ?: PreferencesManager(context).also { INSTANCE = it }
        }
    }

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun putString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value).apply()
    }

    fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value).apply()
    }

    fun putStringSet(key: String, value: MutableSet<String?>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, value).apply()
    }

    fun getString(key: String) = sharedPreferences.getString(key, "")
    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)
    fun getFloat(key: String) = sharedPreferences.getFloat(key, 0f)
    fun getInt(key: String) = sharedPreferences.getInt(key, 0)
    fun getLong(key: String) = sharedPreferences.getLong(key, 0)
    fun getStringSet(key: String) = sharedPreferences.getStringSet(key, mutableSetOf())
}