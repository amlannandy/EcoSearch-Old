package com.aknindustries.ecosearch.api

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.aknindustries.ecosearch.activities.LoginActivity
import com.aknindustries.ecosearch.activities.SplashActivity
import com.aknindustries.ecosearch.models.User
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
                val token = res.getString(Constants.DATA)
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

    fun getCurrentUser(activity: Activity) {
        val token = getTokenFromLocalStorage(activity)
        if (token != null) {
            val request = object: JsonObjectRequest(
                Method.GET,
                "$baseUrl/get-current-user",
                null,
                { res ->
                    val userJSONData = res.getJSONObject(Constants.DATA)
                    saveUserToLocalStorage(activity, userJSONData)
                    getUserFromLocalStorage(activity)
                    when (activity) {
                        is SplashActivity -> activity.loadHome()
                    }
                },
                { error ->
                    val errorMessage = Constants.getApiErrorMessage(error)
                    Log.d("User Loading Error", errorMessage)
                    when (activity) {
                        is SplashActivity -> activity.loadLogin()
                    }
                },
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers[Constants.AUTHORIZATION_HEADER] = Constants.getBearerToken(token)
                    return headers
                }
            }
            VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
        } else {
            when (activity) {
                is SplashActivity -> activity.loadLogin()
            }
        }
    }

    private fun saveTokenToLocalStorage(activity: Activity, token: String) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(Constants.ECO_SEARCH_TOKEN, token)
        editor.apply()
    }

    private fun getTokenFromLocalStorage(activity: Activity): String? {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(Constants.ECO_SEARCH_TOKEN, null)
    }

    private fun saveUserToLocalStorage(activity: Activity, userJSONObject: JSONObject) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(Constants.LOCAL_USER, userJSONObject.toString())
        editor.apply()
    }

    private fun getUserFromLocalStorage(activity: Activity): User? {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val userString = sharedPreferences.getString(Constants.LOCAL_USER, null) ?: return null
        val userJSONObject = JSONObject(userString)
        val user = User.fromJSON(userJSONObject)
        return user
    }

}