package org.openapitools.client.models

import com.squareup.moshi.Json

class CustomLocationsTypeDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,
    @Json(name = "parentId")
    val parentId: String? = null,
)