package com.itrocket.union.authMain.data

import com.squareup.moshi.Json

data class AuthRequest(
    @Json(name = "login") val login: String,
    @Json(name = "password") val password: String
)