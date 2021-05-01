package com.aknindustries.ecosearch.utils

import android.util.Log
import com.android.volley.VolleyError
import org.json.JSONObject
import java.lang.Exception

object Constants {

    const val BASE_URL = "http://192.168.0.105:5000"
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
    const val CURRENT_PASSWORD = "current_password"
    const val NEW_PASSWORD = "new_password"

    fun getBearerToken(token: String): String {
        return "Bearer $token"
    }

    fun getApiErrorMessage(error: VolleyError) : String {
        return try {
            val errorResponse = error.networkResponse.data
            val jsonObject = JSONObject(String(errorResponse))
            jsonObject.get("msg").toString()
        } catch (e: Exception) {
            Log.d("Internal Server Error", e.message.toString())
            "Internal Server Error"
        }
    }

}