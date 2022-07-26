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
 * @param deleted 
 * @param version 
 * @param dateInsert 
 * @param dateUpdate 
 * @param catalogItemName 
 * @param processed 
 * @param inventoryRecordId 
 * @param extendedInventoryRecord 
 * @param inventoryReportId 
 * @param extendedInventoryReport 
 * @param wasSelectedMain 
 */

data class InventoryReportRecordDto (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int?=null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "processed")
    val processed: kotlin.Boolean? = null,

    @Json(name = "inventoryRecordId")
    val inventoryRecordId: kotlin.String? = null,

    @Json(name = "extendedInventoryRecord")
    val extendedInventoryRecord: InventoryRecordDto? = null,

    @Json(name = "inventoryReportId")
    val inventoryReportId: kotlin.String? = null,

    @Json(name = "extendedInventoryReport")
    val extendedInventoryReport: InventoryReportDto? = null,

    @Json(name = "wasSelectedMain")
    val wasSelectedMain: kotlin.Boolean? = null

)

