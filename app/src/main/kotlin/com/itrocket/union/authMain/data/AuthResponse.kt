package com.itrocket.union.authMain.data

import com.squareup.moshi.Json

data class AuthResponse(
    @Json(name = "accessToken") val accessToken: String?,
    @Json(name = "refreshToken") val refreshToken: String?
)