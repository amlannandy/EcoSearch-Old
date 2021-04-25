package com.aknindustries.ecosearch.models

import com.aknindustries.ecosearch.utils.Constants
import org.json.JSONObject

class User (
    val id: Int,
    val name: String,
    val email: String,
    val username: String,
    val createdAt: String
    ) {

    companion object {
        fun fromJSON(userJSONObject: JSONObject): User {
            return User(
                userJSONObject.getInt(Constants.USER_ID),
                userJSONObject.getString(Constants.NAME),
                userJSONObject.getString(Constants.EMAIL),
                userJSONObject.getString(Constants.USERNAME),
                userJSONObject.getString(Constants.CREATED_AT)
            )
        }
    }

}