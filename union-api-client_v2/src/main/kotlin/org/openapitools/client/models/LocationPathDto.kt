package org.openapitools.client.models

import com.squareup.moshi.Json

data class LocationPathDto(
    @Json(name = "descendantId")
    var descendantId: String,
    @Json(name = "ancestorId")
    var ancestorId: String? = null
)