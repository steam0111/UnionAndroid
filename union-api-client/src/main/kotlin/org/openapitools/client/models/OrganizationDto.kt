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
 * @param name 
 * @param inn 
 * @param kpp 
 * @param actualAddress 
 * @param legalAddress 
 * @param comment 
 * @param employeeId 
 */

data class OrganizationDto (

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

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "inn")
    val inn: kotlin.String? = null,

    @Json(name = "kpp")
    val kpp: kotlin.String? = null,

    @Json(name = "actualAddress")
    val actualAddress: kotlin.String? = null,

    @Json(name = "legalAddress")
    val legalAddress: kotlin.String? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null,

    @Json(name = "employeeId")
    val employeeId: kotlin.String? = null

)
