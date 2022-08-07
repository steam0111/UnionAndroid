package org.openapitools.client.models

import com.squareup.moshi.Json

data class StructuralUnitDtoV2(
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
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "balanceUnit")
    val balanceUnit: Boolean? = null,
    @Json(name = "balanceUnitCode")
    val balanceUnitCode: String? = null,
    @Json(name = "branch")
    val branch: Boolean? = null,
    @Json(name = "parentId")
    val parentId: String? = null,
    @Json(name = "extendedParent")
    val extendedParent: StructuralUnitDtoV2? = null,
    @Json(name = "fullCode")
    val fullCode: String? = null,
)