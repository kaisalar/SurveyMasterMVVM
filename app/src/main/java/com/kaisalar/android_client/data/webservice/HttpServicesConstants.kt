package com.kaisalar.android_client.data.webservice

// Http errors
const val HTTP_RESPONSE_ERROR = "HTTP_RESPONSE_ERROR"

// Http requests tags
const val TAG_CREATE_NEW_ACCOUNT = "TAG_CREATE_NEW_ACCOUNT"
const val TAG_SIGN_IN = "TAG_SIGN_IN"
const val TAG_GET_ALL_SURVEYS = "TAG_GET_ALL_SURVEYS"
const val TAG_ADD_NEW_SURVEY = "TAG_ADD_NEW_SURVEY"
const val TAG_GET_ALL_SURVEY_RESPONSES = "TAG_GET_ALL_SURVEY_RESPONSES"
const val TAG_GET_RESPONSE_ANSWERS = "TAG_GET_RESPONSE_ANSWERS"
const val TAG_GET_CURRENT_USER = "TAG_GET_CURRENT_USER"
const val TAG_GET_SURVEY_REPORT = "TAG_GET_SURVEY_REPORT"
const val TAG_ADD_USER_FOR_SURVEY = "TAG_ADD_USER_FOR_SURVEY"
const val TAG_GET_SURVEY_USERS = "TAG_GET_SURVEY_USERS"
const val TAG_DELETE_SURVEY_USER = "TAG_DELETE_SURVEY_USER"
const val TAG_DELETE_SURVEY = "TAG_DELETE_SURVEY"


// URLs
//const val URL_BASE = "http://192.168.43.116:5000"
//const val URL_BASE = "https://survey-master-server.herokuapp.com"
const val URL_BASE = "https://survey-master-v1.herokuapp.com"
//const val URL_BASE = "https://ite-sm-server.herokuapp.com/"
const val URL_API = "$URL_BASE/api"
const val URL_AUTH_END_POINT = "$URL_API/auth"
const val URL_USERS_END_POINT = "$URL_API/users"
const val URL_SURVEYS_END_POINT = "$URL_API/surveys"
const val URL_SURVEYS_USERS_ENDPOINT = "$URL_API/surveyUsers"
const val URL_CURRENT_USER = "$URL_USERS_END_POINT/me"

// Dynamic URLs
fun SURVEY_URL(surveyId: String) = "$URL_SURVEYS_END_POINT/$surveyId"
fun SURVEY_ALL_RESPONSES_URL(surveyId: String) = "${SURVEY_URL(surveyId)}/responses"
fun SURVEY_RESPONSE_URL(surveyId: String, responseId: String) =
        "${SURVEY_ALL_RESPONSES_URL(surveyId)}/$responseId"
fun SURVEY_REPORTS_URL(surveyId: String) = "${SURVEY_URL(surveyId)}/report"
fun SURVEY_USERS_URL(surveyId: String) = "$URL_SURVEYS_USERS_ENDPOINT/$surveyId"
fun SURVEY_USER_URL(surveyId: String, userId: String) = "$URL_SURVEYS_USERS_ENDPOINT/$surveyId/$userId"

