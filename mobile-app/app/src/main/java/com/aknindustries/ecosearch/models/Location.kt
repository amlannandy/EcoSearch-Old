package com.aknindustries.ecosearch.models

import com.aknindustries.ecosearch.utils.Constants
import org.json.JSONObject

class Location (
    val latitude: Double,
    val longitude: Double,
    val address: String,
) {

    companion object {
        fun fromJSON(locationJSONObject: JSONObject): Location {
            return Location(
                latitude = locationJSONObject.getDouble(Constants.LATITUDE),
                longitude = locationJSONObject.getDouble(Constants.LONGITUDE),
                address = locationJSONObject.getString(Constants.ADDRESS),
            )
        }
    }

}