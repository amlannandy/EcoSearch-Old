package com.aknindustries.ecosearch.api

import android.content.Context
import android.util.Log
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.VolleySingleton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class Auth(context: Context) {

    private val currentContext = context
    private val baseUrl = Constants.BASE_URL + Constants.API_VERSION + Constants.AUTH_CONTROLLER

    fun login(email: String, password: String) {
        val loginPostData = HashMap<String, String>()
        loginPostData[Constants.EMAIL] = email
        loginPostData[Constants.PASSWORD] = password
        val request = JsonObjectRequest(
            Request.Method.POST,
            "$baseUrl/login",
            JSONObject(loginPostData as Map<*, *>),
            { res ->
                val token = res.getString("data")
                Log.d("Response", token)
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                Log.d("Error", errorMessage)
            }
        )
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

}