package com.aknindustries.ecosearch.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.MediaStore
import android.util.Log
import com.android.volley.VolleyError
import org.json.JSONObject
import java.lang.Exception

object Constants {

    const val BASE_URL = "http://192.168.0.104:5000"
    const val API_VERSION = "/api/v1"
    const val AUTH_CONTROLLER = "/auth"
    const val RECORDS_CONTROLLER = "/records"
    const val DATA = "data"
    const val MESSAGE = "msg"
    const val NULL = "null"

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

    // Record model
    const val RECORD_ID = "id"
    const val TITLE = "title"
    const val DESCRIPTION = "description"
    const val TYPE = "type"
    const val IMAGE = "image"
    const val LOCATION = "location"
    const val LABEL = "label"
    const val CONFIDENCE = "confidence"

    // Location
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val ADDRESS = "address"

    // Permissions
    const val USE_GALLERY_CODE = 1
    const val LOCATION_PERMISSION_CODE = 3

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

    fun useGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, USE_GALLERY_CODE)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(activity: Activity): Location? {
        val locationManager: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

}