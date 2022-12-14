package org.openapitools.client.models

import com.squareup.moshi.Json

data class AccountingObjectUnionImageDtoV2(
    @Json(name = "accountingObjectId")
    val accountingObjectId: kotlin.String,

    @Json(name = "unionImageId")
    val unionImageId: kotlin.String,

    @Json(name = "isMainImage")
    val isMainImage: Boolean,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "userInserted")
    val userInserted: kotlin.String? = null,

    @Json(name = "userUpdated")
    val userUpdated: kotlin.String? = null,
)