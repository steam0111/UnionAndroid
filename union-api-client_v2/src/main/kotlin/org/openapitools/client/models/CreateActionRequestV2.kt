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

import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.ExtendedActionAccountingObjectRecordV2
import org.openapitools.client.models.ExtendedActionRemainsRecordV2
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.StructuralUnitDtoV2

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
 * @param code 
 * @param userInserted 
 * @param userUpdated 
 * @param creationDate 
 * @param completionDate 
 * @param internalNumber 
 * @param structuralUnitId 
 * @param locationFromId 
 * @param locationToId 
 * @param molId 
 * @param exploitingId 
 * @param actionStatusId 
 * @param actionTypeId 
 * @param structuralUnitFromId 
 * @param structuralUnitToId 
 * @param comment 
 * @param balanceUnitId 
 * @param molReceivingId 
 * @param extendedMolReceiving 
 * @param extendedStructuralUnit 
 * @param extendedLocationFrom 
 * @param extendedLocationTo 
 * @param extendedMol 
 * @param extendedExploiting 
 * @param extendedActionStatus 
 * @param extendedActionType 
 * @param extendedStructuralUnitFrom 
 * @param extendedStructuralUnitTo 
 * @param actionBaseId 
 * @param extendedActionBase 
 * @param records 
 * @param extendedActionAccountingObjectRecords 
 * @param extendedActionRemainsRecords 
 */

data class CreateActionRequestV2 (

    @Json(name = "id")
    override val id: kotlin.String,

    @Json(name = "deleted")
    override val deleted: kotlin.Boolean,

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

    @Json(name = "creationDate")
    val creationDate: kotlin.String? = null,

    @Json(name = "completionDate")
    val completionDate: kotlin.String? = null,

    @Json(name = "internalNumber")
    val internalNumber: kotlin.String? = null,

    @Json(name = "structuralUnitId")
    val structuralUnitId: kotlin.String? = null,

    @Json(name = "locationFromId")
    val locationFromId: kotlin.String? = null,

    @Json(name = "locationToId")
    val locationToId: kotlin.String? = null,

    @Json(name = "molId")
    val molId: kotlin.String? = null,

    @Json(name = "exploitingId")
    val exploitingId: kotlin.String? = null,

    @Json(name = "actionStatusId")
    val actionStatusId: kotlin.String? = null,

    @Json(name = "actionTypeId")
    val actionTypeId: kotlin.String? = null,

    @Json(name = "structuralUnitFromId")
    val structuralUnitFromId: kotlin.String? = null,

    @Json(name = "structuralUnitToId")
    val structuralUnitToId: kotlin.String? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null,

    @Json(name = "balanceUnitId")
    val balanceUnitId: kotlin.String? = null,

    @Json(name = "molReceivingId")
    val molReceivingId: kotlin.String? = null,

    @Json(name = "extendedMolReceiving")
    val extendedMolReceiving: EmployeeDtoV2? = null,

    @Json(name = "extendedStructuralUnit")
    val extendedStructuralUnit: StructuralUnitDtoV2? = null,

    @Json(name = "extendedLocationFrom")
    val extendedLocationFrom: LocationDtoV2? = null,

    @Json(name = "extendedLocationTo")
    val extendedLocationTo: LocationDtoV2? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: EmployeeDtoV2? = null,

    @Json(name = "extendedActionStatus")
    val extendedActionStatus: EnumDtoV2? = null,

    @Json(name = "extendedActionType")
    val extendedActionType: EnumDtoV2? = null,

    @Json(name = "extendedStructuralUnitFrom")
    val extendedStructuralUnitFrom: StructuralUnitDtoV2? = null,

    @Json(name = "extendedStructuralUnitTo")
    val extendedStructuralUnitTo: StructuralUnitDtoV2? = null,

    @Json(name = "actionBaseId")
    val actionBaseId: kotlin.String? = null,

    @Json(name = "extendedActionBase")
    val extendedActionBase: EnumDtoV2? = null,

    @Json(name = "records")
    val records: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "extendedActionAccountingObjectRecords")
    val extendedActionAccountingObjectRecords: kotlin.collections.List<ExtendedActionAccountingObjectRecordV2>? = null,

    @Json(name = "extendedActionRemainsRecords")
    val extendedActionRemainsRecords: kotlin.collections.List<ExtendedActionRemainsRecordV2>? = null

): DeletedItemDto

