package com.aknindustries.ecosearch.api

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.aknindustries.ecosearch.activities.LoginActivity
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.VolleySingleton
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class Auth(context: Context) {

    private val currentContext = context
    private val baseUrl = Constants.BASE_URL + Constants.API_VERSION + Constants.AUTH_CONTROLLER

    fun login(activity: LoginActivity, email: String, password: String) {
        val loginPostData = HashMap<String, String>()
        loginPostData[Constants.EMAIL] = email
        loginPostData[Constants.PASSWORD] = password
        val request = JsonObjectRequest(
            Request.Method.POST,
            "$baseUrl/login",
            JSONObject(loginPostData as Map<*, *>),
            { res ->
                val token = res.getString("data")
                saveTokenToLocalStorage(activity, token)
                activity.loginSuccess()
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.loginFailure(errorMessage)
            }
        )
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

    private fun saveTokenToLocalStorage(activity: Activity, token: String) {
        val sharedPreferences = activity.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(Constants.ECO_SEARCH_TOKEN, token)
        editor.apply()
    }

}