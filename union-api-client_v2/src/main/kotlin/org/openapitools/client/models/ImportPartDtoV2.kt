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
 * @param `value` 
 * @param entityModelId 
 * @param dateTime 
 */

data class ImportPartDtoV2 (

    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "value")
    val `value`: kotlin.collections.List<AccountingObjectDtoV2>? = null,

    @Json(name = "entityModelId")
    val entityModelId: kotlin.String? = null,

    @Json(name = "dateTime")
    val dateTime: kotlin.String? = null

)
