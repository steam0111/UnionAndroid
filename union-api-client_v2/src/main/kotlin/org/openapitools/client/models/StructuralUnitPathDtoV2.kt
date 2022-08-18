package org.openapitools.client.models

import com.squareup.moshi.Json

data class StructuralUnitPathDtoV2(
    @Json(name = "ancestorId")
    val ancestorId: String? = null,
    @Json(name = "descendantId")
    val descendantId: String
)