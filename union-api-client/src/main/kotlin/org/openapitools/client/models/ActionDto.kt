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
 * @param creationDate
 * @param completionDate
 * @param code
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

data class ActionDto(

    @Json(name = "id")
    val id: kotlin.String,

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

    @Json(name = "creationDate")
    val creationDate: kotlin.String? = null,

    @Json(name = "completionDate")
    val completionDate: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

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
    val extendedOrganization: OrganizationDto? = null,

    @Json(name = "extendedLocationFrom")
    val extendedLocationFrom: LocationDto? = null,

    @Json(name = "extendedLocationTo")
    val extendedLocationTo: LocationDto? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDto? = null,

    @Json(name = "extendedExploiting")
    val extendedExploiting: EmployeeDto? = null,

    @Json(name = "extendedActionStatus")
    val extendedActionStatus: ActionStatusDto? = null,

    @Json(name = "extendedActionType")
    val extendedActionType: ActionTypeDto? = null,

    @Json(name = "extendedDepartmentFrom")
    val extendedDepartmentFrom: DepartmentDto? = null,

    @Json(name = "extendedDepartmentTo")
    val extendedDepartmentTo: DepartmentDto? = null,

    @Json(name = "actionBaseId")
    val actionBaseId: kotlin.String? = null,

    @Json(name = "extendedActionBase")
    val extendedActionBase: ActionBaseDto? = null

)

