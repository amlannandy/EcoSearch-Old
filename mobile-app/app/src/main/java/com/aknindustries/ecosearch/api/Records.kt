package com.aknindustries.ecosearch.api

import android.content.Context
import android.net.Uri
import android.util.Log
import com.aknindustries.ecosearch.activities.AddRecordActivity
import com.aknindustries.ecosearch.fragments.RecordsFragment
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.MultipartRequest
import com.aknindustries.ecosearch.utils.VolleySingleton
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class Records(context: Context) {

    private val currentContext = context
    private val baseUrl = Constants.BASE_URL + Constants.API_VERSION + Constants.RECORDS_CONTROLLER

    fun fetchUserRecords(fragment: RecordsFragment) {
        val token = Auth(fragment.requireActivity().applicationContext).getTokenFromLocalStorage(fragment.requireActivity())!!
        val request = object: JsonObjectRequest(
            Method.GET,
            "$baseUrl/",
            null,
            { res ->
                val data = res.getJSONArray(Constants.DATA)
                val records = ArrayList<Record>()
                for (i in 0 until data.length()) {
                    val recordJSONObject = data.get(i) as JSONObject
                    val record = Record.fromJSON(recordJSONObject)
                    records.add(record)
                }
                for (record in records) {
                    Log.d(record.title, record.description)
                }
            },
            { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                Log.d("Error", errorMessage)
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

    fun addRecord(activity: AddRecordActivity, postData: HashMap<String, Any?>, imageUri: Uri) {
        val token = Auth(activity.applicationContext).getTokenFromLocalStorage(activity)!!
        val request = object: JsonObjectRequest(
            Method.POST,
            "$baseUrl/",
            JSONObject(postData as Map<*, *>),
            { res ->
                val jsonData = res.getJSONObject(Constants.DATA)
                val id = jsonData.getInt("id")
                addImageToRecord(activity, id, postData[Constants.TITLE] as String, imageUri)
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

    private fun addImageToRecord(activity: AddRecordActivity, id: Int, imageName: String, imageUri: Uri) {
        val token = Auth(activity.applicationContext).getTokenFromLocalStorage(activity)!!
        val request = object : MultipartRequest(
            Method.POST,
            "${baseUrl}/upload-image/${id}",
            Response.Listener { res ->
                Log.d("Date", res.data.toString())
                activity.addRecordSuccess()
            },
            Response.ErrorListener { error ->
                val errorMessage = Constants.getApiErrorMessage(error)
                activity.addRecordFailure(errorMessage)
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                val params = HashMap<String, FileDataPart>()
                val inputStream = activity.contentResolver.openInputStream(imageUri)
                var imageData: ByteArray?
                inputStream?.buffered()?.use {
                    imageData = it.readBytes()
                    params["image"] = FileDataPart("${imageName}.jpeg", imageData!!, "image/jpeg")
                }
                return params
            }
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers[Constants.AUTHORIZATION_HEADER] = Constants.getBearerToken(token)
                return headers
            }
        }
        VolleySingleton.getInstance(currentContext).addToRequestQueue(request)
    }

}