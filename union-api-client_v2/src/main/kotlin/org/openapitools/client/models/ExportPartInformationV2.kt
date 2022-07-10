/**
 * OpenAPI definition
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 *
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import com.squareup.moshi.Json

/**
 *
 *
 * @param id
 * @param count
 * @param entityModel
 */

data class ExportPartInformationV2(

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "count")
    val count: kotlin.Int? = null,

    @Json(name = "entityModel")
    val entityModel: EntityModelV2,

    @Json(name = "table")
    val table: String

)

