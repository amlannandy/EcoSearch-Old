package com.aknindustries.ecosearch.utils

import com.android.volley.VolleyError
import org.json.JSONObject

object Constants {

    const val BASE_URL = "http://192.168.0.103:5000"
    const val API_VERSION = "/api/v1"
    const val AUTH_CONTROLLER = "/auth"
    const val DATA = "data"
    const val MESSAGE = "msg"

    const val APP_PREFERENCES = "eco_search"
    const val ECO_SEARCH_TOKEN = "eco_search_token"
    const val LOCAL_USER = "eco_search_user"
    const val AUTHORIZATION_HEADER = "Authorization"

    // User model
    const val USER_ID = "id"
    const val NAME = "name"
    const val EMAIL = "email"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val CREATED_AT = "created_at"

    fun getBearerToken(token: String): String {
        return "Bearer $token"
    }

    fun getApiErrorMessage(error: VolleyError) : String {
        val jsonObject = JSONObject(String(error.networkResponse.data))
        return jsonObject.get("msg").toString()
    }

}