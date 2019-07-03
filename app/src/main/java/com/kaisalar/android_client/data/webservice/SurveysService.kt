package com.kaisalar.android_client.data.webservice

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Klaxon
import com.kaisalar.android_client.data.model.*
import org.json.JSONObject
import java.lang.reflect.Method

class SurveysService(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: SurveysService? = null
        fun getInstance(context: Context) = synchronized(this) {
            INSTANCE
                ?: SurveysService(context).also { INSTANCE = it }
        }
    }

    private val requestQueue: HttpRequestQueue by lazy {
        HttpRequestQueue.getInstance(context)
    }

    private val token: String by lazy {
        AuthService.getInstance(context).getAuthToken()
    }

    fun getSurveys(onSuccess: (List<SurveyForGetting>) -> Unit, onFailure: () -> Unit) {
        val request = object : JsonRequest<List<SurveyForGetting>>(
            Method.GET,
            URL_SURVEYS_END_POINT,
            null,
            Response.Listener<List<SurveyForGetting>> {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<List<SurveyForGetting>> {
                val data = response?.data ?: return Response.success(listOf(), cacheEntry)
                val json = String(data)
                val surveys = Klaxon().parseArray<SurveyForGetting>(json)
                return Response.success(surveys, cacheEntry)
            }
        }
        request.tag = TAG_GET_ALL_SURVEYS

        requestQueue.addToRequestQueue(request)
    }

    fun addSurvey(survey: SurveyForCreation, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val request = object : StringRequest(
            Method.POST,
            URL_SURVEYS_END_POINT,
            Response.Listener {
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
                val json = Klaxon().toJsonString(survey)
                return json.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }
        }
        request.tag = TAG_ADD_NEW_SURVEY

        requestQueue.addToRequestQueue(request)
    }

    fun getSurveyResponses(surveyId: String, onSuccess: (List<ResponseForGetting>) -> Unit, onFailure: () -> Unit) {
        val url = SURVEY_ALL_RESPONSES_URL(surveyId)
        val request = object : JsonRequest<List<ResponseForGetting>>(
            Method.GET,
            url,
            null,
            Response.Listener {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<List<ResponseForGetting>> {
                val json = String(response?.data!!)
                val responses = Klaxon().parseArray<ResponseForGetting>(json)
                return Response.success(responses, cacheEntry)
            }
        }
        request.tag = TAG_GET_ALL_SURVEY_RESPONSES

        requestQueue.addToRequestQueue(request)
    }

    fun getSurveyResponseAnswers(
        surveyId: String,
        responseId: String,
        onSuccess: (List<AnswerForGetting>) -> Unit,
        onFailure: () -> Unit
    ) {
        val url = SURVEY_RESPONSE_URL(surveyId, responseId)
        val request = object : JsonRequest<List<AnswerForGetting>>(
            Method.GET,
            url,
            null,
            Response.Listener {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<List<AnswerForGetting>> {
                val json = String(response?.data!!)
                val jsonAnswers = JSONObject(json).getJSONArray("answers")
                val answers = mutableListOf<AnswerForGetting>()
                for (i in 0 until jsonAnswers.length()) {
                    val obj = jsonAnswers.getJSONObject(i)

                    when (obj.getString("type")) {
                        ANSWER_TEXT -> {
                            val answer = Klaxon().parse<TextAnswerForGetting>(obj.toString(0))
                            answers.add(answer!!)
                        }
                        ANSWER_MULTIPLE_CHOICE -> {
                            val answer = Klaxon().parse<MultipleChoiceAnswerForGetting>(obj.toString(0))
                            answers.add(answer!!)
                        }
                        ANSWER_SINGLE_NUMBER_VALUE -> {
                            val answer = Klaxon().parse<SingleNumberValueAnswerForGetting>(obj.toString(0))
                            answers.add(answer!!)
                        }
                        ANSWER_RANGE -> {
                            val answer = Klaxon().parse<RangeAnswerForGetting>(obj.toString(0))
                            answers.add(answer!!)
                        }
                    }
                }
                return Response.success(answers, cacheEntry)
            }
        }
        request.tag = TAG_GET_RESPONSE_ANSWERS

        requestQueue.addToRequestQueue(request)
    }

    fun getSurveyReport(surveyId: String, onSuccess: (ReportForGetting) -> Unit, onFailure: () -> Unit) {
        val url = SURVEY_REPORTS_URL(surveyId)
        val request = object : JsonRequest<ReportForGetting> (
            Method.GET,
            url,
            null,
            Response.Listener<ReportForGetting> {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<ReportForGetting> {
                val json = String(response?.data!!)
                val report = Klaxon().parse<ReportForGetting>(json)
                return Response.success(report, cacheEntry)
            }
        }
        request.tag = TAG_GET_SURVEY_REPORT

        requestQueue.addToRequestQueue(request)
    }

    fun getSurveyUsers(surveyId: String, onSuccess: (List<UserForSurvey>) -> Unit, onFailure: () -> Unit) {
        val url = SURVEY_USERS_URL(surveyId)
        val request = object : JsonRequest<List<UserForSurvey>> (
            Method.GET,
            url,
            null,
            Response.Listener {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFailure()
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<List<UserForSurvey>> {
                val json = String(response?.data!!)
                val users = Klaxon().parseArray<UserForSurvey>(json)
                return Response.success(users, cacheEntry)
            }
        }
    }

    fun deleteSurvey(surveyId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val url = SURVEY_URL(surveyId)
        val request = object: StringRequest(
            Method.DELETE,
            url,
            Response.Listener {
                onSuccess()
            },
            Response.ErrorListener {
                onFailure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getAuthHeaders()
            }
        }

        requestQueue.addToRequestQueue(request)
    }

    private fun getAuthHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        headers["x-auth-token"] = token
        return headers
    }
}