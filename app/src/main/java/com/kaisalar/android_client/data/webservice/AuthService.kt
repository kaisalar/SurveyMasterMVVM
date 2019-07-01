package com.kaisalar.android_client.data.webservice

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.auth0.android.jwt.JWT
import com.beust.klaxon.Klaxon
import com.kaisalar.android_client.data.model.UserForCreation
import com.kaisalar.android_client.data.model.UserPublicInfo
import com.kaisalar.android_client.util.PREF_AUTH_TOKEN_KEY
import com.kaisalar.android_client.util.PreferencesManager

class AuthService(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: AuthService? = null
        fun getInstance(context: Context) = synchronized(this) {
            INSTANCE
                ?: AuthService(context).also { INSTANCE = it }
        }
    }

    private val requestQueue: HttpRequestQueue by lazy {
        HttpRequestQueue.getInstance(context)
    }

    private val preferencesManager: PreferencesManager by lazy {
        PreferencesManager.getInstance(context)
    }

    fun createAccount(user: UserForCreation, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val request = object : StringRequest(
            Method.POST,
            URL_USERS_END_POINT,
            Response.Listener {
                saveAuthToken(it)
                onSuccess()
            },
            Response.ErrorListener {
                onFailure("")
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val json = Klaxon().toJsonString(user)
                return json.toByteArray()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                val token = response?.headers?.get("x-auth-token")
                if (token != null && token.isNotEmpty() && token.isNotBlank())
                    return Response.success(token, cacheEntry)
                return Response.error(VolleyError(response))
            }
        }
        request.tag = TAG_CREATE_NEW_ACCOUNT

        requestQueue.addToRequestQueue(request)
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        class SignInfo(val email: String, val password: String)
        val signInfo = SignInfo(email, password)

        val request = object : StringRequest(
            Method.POST,
            URL_AUTH_END_POINT,
            Response.Listener {
                saveAuthToken(it)
                onSuccess()
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                val json = Klaxon().toJsonString(signInfo)
                return json.toByteArray()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                val data = response?.data ?: return Response.error(VolleyError(response))
                val token = String(data)
                if (token.isBlank() || token.isEmpty()) return Response.error(VolleyError(response))
                return Response.success(token, cacheEntry)
            }
        }
        request.tag = TAG_SIGN_IN

        requestQueue.addToRequestQueue(request)
    }

    fun signOut() = clearAuthToken()

    fun isUserSigned() = getAuthToken().isNotBlank() && getAuthToken().isNotEmpty()

    fun getUserPublicInfo() : UserPublicInfo {
        val jwt = JWT(getAuthToken())
        val firstName = jwt.getClaim("firstName").asString()
        val lastName = jwt.getClaim("lastName").asString()
        val email = jwt.getClaim("email").asString()
        return UserPublicInfo(firstName ?: "",  lastName ?: "", email ?: "")
    }

    fun getAuthToken(): String {
        return preferencesManager.getString(PREF_AUTH_TOKEN_KEY)
    }

    private fun saveAuthToken(token: String) {
        preferencesManager.putString(PREF_AUTH_TOKEN_KEY, token)
    }

    private fun clearAuthToken() {
        preferencesManager.putString(PREF_AUTH_TOKEN_KEY, "")
    }

    fun cancelSignInRequest() {
        requestQueue.cancelFromRequestQueue(TAG_SIGN_IN)
    }

    fun cancelCreateAccountRequest() {
        requestQueue.cancelFromRequestQueue(TAG_CREATE_NEW_ACCOUNT)
    }
}