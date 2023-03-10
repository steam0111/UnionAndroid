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
 * @param repairRecordId 
 * @param repairRecordRemainsId 
 * @param remainsId 
 * @param count 
 */

data class SaveRepairRequestRemainsItemV2 (

    @Json(name = "repairRecordId")
    val repairRecordId: kotlin.String? = null,

    @Json(name = "repairRecordRemainsId")
    val repairRecordRemainsId: kotlin.String? = null,

    @Json(name = "remainsId")
    val remainsId: kotlin.String? = null,

    @Json(name = "count")
    val count: java.math.BigDecimal? = null

)

