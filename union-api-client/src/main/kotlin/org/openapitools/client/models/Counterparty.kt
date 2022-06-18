package org.openapitools.client.models

import com.squareup.moshi.Json

data class Counterparty (

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

    @Json(name = "actualAddress")
    val actualAddress: String? = null,

    @Json(name = "legalAddress")
    val legalAddress: String? = null,

    @Json(name = "inn")
    val inn: String? = null,

    @Json(name = "kpp")
    val kpp: String? = null

)