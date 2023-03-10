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
 * @param repairId 
 * @param extendedRepair 
 * @param locationId 
 * @param extendedLocation 
 * @param previousAccountingObjectStatusId 
 * @param extendedPreviousAccountingObjectStatusId 
 * @param repairRecordStatusId 
 * @param extendedRepairRecordStatus 
 * @param accountingObjectId 
 * @param extendedAccountingObject 
 */

data class RepairRecordDto (

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

    @Json(name = "repairId")
    val repairId: kotlin.String? = null,

    @Json(name = "extendedRepair")
    val extendedRepair: RepairDto? = null,

    @Json(name = "locationId")
    val locationId: kotlin.String? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: LocationDto? = null,

    @Json(name = "previousAccountingObjectStatusId")
    val previousAccountingObjectStatusId: kotlin.String? = null,

    @Json(name = "extendedPreviousAccountingObjectStatusId")
    val extendedPreviousAccountingObjectStatusId: AccountingObjectStatusDto? = null,

    @Json(name = "repairRecordStatusId")
    val repairRecordStatusId: kotlin.String? = null,

    @Json(name = "extendedRepairRecordStatus")
    val extendedRepairRecordStatus: RepairRecordStatusDto? = null,

    @Json(name = "accountingObjectId")
    val accountingObjectId: kotlin.String? = null,

    @Json(name = "extendedAccountingObject")
    val extendedAccountingObject: AccountingObjectDto? = null

)

