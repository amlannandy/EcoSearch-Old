package com.aknindustries.ecosearch.api

import android.content.Context
import com.aknindustries.ecosearch.activities.AddRecordActivity
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.VolleySingleton
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class Records(context: Context) {

    private val currentContext = context
    private val baseUrl = Constants.BASE_URL + Constants.API_VERSION + Constants.RECORDS_CONTROLLER

    fun addRecord(activity: AddRecordActivity, postData: HashMap<String, String>) {
        val token = Auth(activity.applicationContext).getTokenFromLocalStorage(activity)!!
        val request = object: JsonObjectRequest(
            Method.POST,
            "$baseUrl/",
            JSONObject(postData as Map<*, *>),
            {
                activity.addRecordSuccess()
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.addRecordFailure(errorMessage)
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

}