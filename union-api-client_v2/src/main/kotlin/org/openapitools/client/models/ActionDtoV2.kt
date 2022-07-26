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
 * @param code 
 * @param creationDate 
 * @param completionDate 
 * @param internalNumber 
 * @param organizationId 
 * @param locationFromId 
 * @param locationToId 
 * @param molId 
 * @param exploitingId 
 * @param actionStatusId 
 * @param actionTypeId 
 * @param departmentFromId 
 * @param departmentToId 
 * @param comment 
 * @param extendedOrganization 
 * @param extendedLocationFrom 
 * @param extendedLocationTo 
 * @param extendedMol 
 * @param extendedExploiting 
 * @param extendedActionStatus 
 * @param extendedActionType 
 * @param extendedDepartmentFrom 
 * @param extendedDepartmentTo 
 * @param actionBaseId 
 * @param extendedActionBase 
 */

data class ActionDtoV2 (

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

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "creationDate")
    val creationDate: kotlin.String? = null,

    @Json(name = "completionDate")
    val completionDate: kotlin.String? = null,

    @Json(name = "internalNumber")
    val internalNumber: kotlin.String? = null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String? = null,

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

    @Json(name = "departmentFromId")
    val departmentFromId: kotlin.String? = null,

    @Json(name = "departmentToId")
    val departmentToId: kotlin.String? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: OrganizationDtoV2? = null,

    @Json(name = "extendedLocationFrom")
    val extendedLocationFrom: LocationDtoV2? = null,

    @Json(name = "extendedLocationTo")
    val extendedLocationTo: LocationDtoV2? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: EmployeeDtoV2? = null,

    @Json(name = "extendedActionStatus")
    val extendedActionStatus: ActionStatusDtoV2? = null,

    @Json(name = "extendedActionType")
    val extendedActionType: ActionTypeDtoV2? = null,

    @Json(name = "extendedDepartmentFrom")
    val extendedDepartmentFrom: DepartmentDtoV2? = null,

    @Json(name = "extendedDepartmentTo")
    val extendedDepartmentTo: DepartmentDtoV2? = null,

    @Json(name = "actionBaseId")
    val actionBaseId: kotlin.String? = null,

    @Json(name = "extendedActionBase")
    val extendedActionBase: ActionBaseDtoV2? = null

)

