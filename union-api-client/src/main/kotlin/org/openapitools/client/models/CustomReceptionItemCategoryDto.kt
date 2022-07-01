package org.openapitools.client.models

import com.squareup.moshi.Json

data class CustomReceptionItemCategoryDto(
    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String
)