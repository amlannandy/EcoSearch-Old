package com.aknindustries.ecosearch.utils

import com.android.volley.VolleyError
import org.json.JSONObject

object Constants {

    const val BASE_URL = "http://192.168.0.103:5000"
    const val API_VERSION = "/api/v1"
    const val AUTH_CONTROLLER = "/auth"

    const val EMAIL = "email"
    const val PASSWORD = "password"

    const val ECO_SEARCH_TOKEN = "eco_search_token"

    fun getApiErrorMessage(error: VolleyError) : String {
        val jsonObject = JSONObject(String(error.networkResponse.data))
        return jsonObject.get("msg").toString()
    }

}