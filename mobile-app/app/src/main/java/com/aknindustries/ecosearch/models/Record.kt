package com.aknindustries.ecosearch.models

import com.aknindustries.ecosearch.utils.Constants
import org.json.JSONObject

class Record (
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val type: String,
    val createdAt: String,
    val location: Location,
    val label: String?,
    val confidence: String?,
) {

    companion object {
        fun fromJSON(recordJSONObject: JSONObject): Record {
            return Record(
                id = recordJSONObject.getInt(Constants.RECORD_ID),
                title = recordJSONObject.getString(Constants.TITLE),
                description = recordJSONObject.getString(Constants.DESCRIPTION),
                image = recordJSONObject.getString(Constants.IMAGE),
                type = recordJSONObject.getString(Constants.TYPE),
                createdAt = recordJSONObject.getString(Constants.CREATED_AT),
                location = Location.fromJSON(recordJSONObject.getJSONObject(Constants.LOCATION)),
                label = recordJSONObject.getString(Constants.LABEL),
                confidence = recordJSONObject.getString(Constants.CONFIDENCE),
            )
        }
    }

}