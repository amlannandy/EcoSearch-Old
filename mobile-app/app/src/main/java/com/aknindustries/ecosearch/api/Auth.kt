package com.aknindustries.ecosearch.api

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.aknindustries.ecosearch.activities.*
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

    fun register(activity: RegisterActivity, registrationData: HashMap<String, String>) {
        val request = JsonObjectRequest(
            Request.Method.POST,
            "$baseUrl/register",
            JSONObject(registrationData as Map<*, *>),
            { res ->
                val token = res.getString(Constants.DATA)
                saveTokenToLocalStorage(activity, token)
                activity.registrationSuccess()
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.registrationFailure(errorMessage)
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
                        is ProfileActivity -> activity.updateProfileSuccess()
                    }
                },
                { error ->
                    val errorMessage = Constants.getApiErrorMessage(error)
                    when (activity) {
                        is SplashActivity -> activity.loadLogin()
                        is ProfileActivity -> activity.updateProfileFailure(errorMessage)
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

    fun logOut(activity: Activity) {
        deleteTokenFromLocalStorage(activity)
        deleteUserFromLocalStorage(activity)
    }

    fun updateInfo(activity: ProfileActivity, putData: HashMap<String, String>) {
        val token = getTokenFromLocalStorage(activity)!!
        val request = object: JsonObjectRequest(
            Method.PUT,
            "$baseUrl/update-info",
            JSONObject(putData as Map<*, *>),
            {
                getCurrentUser(activity)
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.updateProfileFailure(errorMessage)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[Constants.AUTHORIZATION_HEADER] = Constants.getBearerToken(token)
                return headers
            }
        }
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

    fun updatePassword(activity: UpdatePasswordActivity, postData: HashMap<String, String>) {
        val token = getTokenFromLocalStorage(activity)!!
        val request = object: JsonObjectRequest(
            Method.PUT,
            "$baseUrl/update-password",
            JSONObject(postData as Map<*, *>),
            { res ->
                val successMessage = res.getString(Constants.MESSAGE)
                activity.updatePasswordSuccess(successMessage)
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.updatePasswordFailure(errorMessage)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[Constants.AUTHORIZATION_HEADER] = Constants.getBearerToken(token)
                return headers
            }
        }
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

    fun sendPasswordResetEmail(activity: ForgotPasswordActivity, email: String) {
        val postData = HashMap<String, String>()
        postData[Constants.EMAIL] = email
        val request = JsonObjectRequest(
            Request.Method.POST,
            "$baseUrl/forgot-password",
            JSONObject(postData as Map<*, *>),
            { res ->
                val successMessage = res.getString(Constants.MESSAGE)
                activity.passwordResetSuccess(successMessage)
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.passwordResetFailure(errorMessage)
            }
        )
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

    fun deleteAccount(activity: DeleteAccountActivity, password: String) {
        val token = getTokenFromLocalStorage(activity)!!
        val data = HashMap<String, String>()
        data[Constants.PASSWORD] = password
        val request = object: JsonObjectRequest(
            Method.PUT,
            "$baseUrl/delete-account",
            JSONObject(data as Map<*, *>),
            { res ->
                val successMessage = res.getString(Constants.MESSAGE)
                activity.deleteAccountSuccess(successMessage)
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.deleteAccountFailure(errorMessage)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[Constants.AUTHORIZATION_HEADER] = Constants.getBearerToken(token)
                return headers
            }
        }
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
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

    fun getTokenFromLocalStorage(activity: Activity): String? {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(Constants.ECO_SEARCH_TOKEN, null)
    }

    private fun deleteTokenFromLocalStorage(activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(Constants.ECO_SEARCH_TOKEN)
        editor.apply()
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

    fun getUserFromLocalStorage(activity: Activity): User? {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val userString = sharedPreferences.getString(Constants.LOCAL_USER, null) ?: return null
        val userJSONObject = JSONObject(userString)
        return User.fromJSON(userJSONObject)
    }

    private fun deleteUserFromLocalStorage(activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(Constants.LOCAL_USER)
        editor.apply()
    }

}