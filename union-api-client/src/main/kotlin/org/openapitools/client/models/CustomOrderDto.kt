package org.openapitools.client.models

import com.squareup.moshi.Json

data class CustomOrderDto (

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean,

    @Json(name = "version")
    val version: Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "number")
    val number: String? = null,

    @Json(name = "summary")
    val summary: String? = null,

    @Json(name = "date")
    val date: String? = null

)