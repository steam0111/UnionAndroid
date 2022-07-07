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
 * @param firstname 
 * @param lastname 
 * @param patronymic 
 * @param birthdate 
 * @param organizationId 
 * @param extendedOrganization 
 * @param post 
 * @param number 
 * @param card 
 * @param nfc 
 * @param employeeStatusId 
 */

data class EmployeeDtoV2 (

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

    @Json(name = "firstname")
    val firstname: kotlin.String? = null,

    @Json(name = "lastname")
    val lastname: kotlin.String? = null,

    @Json(name = "patronymic")
    val patronymic: kotlin.String? = null,

    @Json(name = "birthdate")
    val birthdate: kotlin.String? = null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: OrganizationDtoV2? = null,

    @Json(name = "post")
    val post: kotlin.String? = null,

    @Json(name = "number")
    val number: kotlin.String? = null,

    @Json(name = "card")
    val card: kotlin.String? = null,

    @Json(name = "nfc")
    val nfc: kotlin.String? = null,

    @Json(name = "employeeStatusId")
    val employeeStatusId: kotlin.String? = null

)
