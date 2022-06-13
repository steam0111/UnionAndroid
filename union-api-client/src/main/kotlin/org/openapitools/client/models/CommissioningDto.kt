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
 * @param date 
 * @param organizationId 
 * @param extendedOrganization 
 * @param responsibleId 
 * @param extendedResponsible 
 * @param statusId 
 * @param extendedStatus 
 */

data class CommissioningDto (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "date")
    val date: kotlin.String? = null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: OrganizationDto? = null,

    @Json(name = "responsibleId")
    val responsibleId: kotlin.String? = null,

    @Json(name = "extendedResponsible")
    val extendedResponsible: EmployeeDto? = null,

    @Json(name = "statusId")
    val statusId: kotlin.String? = null,

    @Json(name = "extendedStatus")
    val extendedStatus: ActionStatusDto? = null

)
