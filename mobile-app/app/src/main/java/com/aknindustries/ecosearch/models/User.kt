package com.aknindustries.ecosearch.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val username: String,
    val accessToken: String? = null,
)