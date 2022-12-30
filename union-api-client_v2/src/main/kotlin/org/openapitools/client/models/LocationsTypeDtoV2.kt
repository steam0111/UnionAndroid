package org.openapitools.client.models

import com.squareup.moshi.Json

class LocationsTypeDtoV2(
    @Json(name = "id")
    override val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,
    @Json(name = "parentId")
    val parentId: String? = null,
    @Json(name = "deleted")
    override val deleted: Boolean
) : DeletedItemDto