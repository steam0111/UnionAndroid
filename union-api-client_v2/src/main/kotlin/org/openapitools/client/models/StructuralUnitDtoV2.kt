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
 * @param userInserted 
 * @param userUpdated 
 * @param name 
 * @param balanceUnit 
 * @param balanceUnitCode 
 * @param branch 
 * @param parentId 
 * @param extendedParent 
 * @param fullCode 
 * @param actualBalanceUnitId 
 */

data class StructuralUnitDtoV2 (

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

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "userInserted")
    val userInserted: kotlin.String? = null,

    @Json(name = "userUpdated")
    val userUpdated: kotlin.String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "balanceUnit")
    val balanceUnit: kotlin.Boolean? = null,

    @Json(name = "balanceUnitCode")
    val balanceUnitCode: kotlin.String? = null,

    @Json(name = "branch")
    val branch: kotlin.Boolean? = null,

    @Json(name = "parentId")
    val parentId: kotlin.String? = null,

    @Json(name = "extendedParent")
    val extendedParent: StructuralUnitDtoV2? = null,

    @Json(name = "fullCode")
    val fullCode: kotlin.String? = null,

    @Json(name = "actualBalanceUnitId")
    val actualBalanceUnitId: kotlin.String? = null

)

