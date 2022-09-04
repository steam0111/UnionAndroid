package org.openapitools.client.models

import com.squareup.moshi.Json

data class StyleSettingsDtoV2(
    @Json(name = "mainColor")
    val mainColor: String,

    @Json(name = "mainTextColor")
    val mainTextColor: String,

    @Json(name = "secondaryTextColor")
    val secondaryTextColor: String? = null,

    @Json(name = "appBarBackgroundColor")
    val appBarBackgroundColor: String? = null,

    @Json(name = "appBarTextColor")
    val appBarTextColor: String? = null
)