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
 * @param name 
 * @param dateInsert 
 * @param dateUpdate 
 * @param catalogItemName 
 * @param code 
 * @param organizationId 
 * @param inn 
 * @param kpp 
 * @param actualAddress 
 * @param legalAddress 
 * @param comment 
 * @param premises 
 * @param floor 
 * @param transit 
 * @param rfidValue 
 * @param nfcValue 
 * @param barcodeValue 
 * @param parentId 
 * @param extendedParent 
 * @param locationTypeId 
 */

data class LocationDtoV2 (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int?=null,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String? = null,

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

    @Json(name = "premises")
    val premises: kotlin.String? = null,

    @Json(name = "floor")
    val floor: kotlin.String? = null,

    @Json(name = "transit")
    val transit: kotlin.Boolean? = null,

    @Json(name = "rfidValue")
    val rfidValue: kotlin.String? = null,

    @Json(name = "nfcValue")
    val nfcValue: kotlin.String? = null,

    @Json(name = "barcodeValue")
    val barcodeValue: kotlin.String? = null,

    @Json(name = "parentId")
    val parentId: kotlin.String? = null,

    @Json(name = "extendedParent")
    val extendedParent: LocationDtoV2? = null,

    @Json(name = "locationTypeId")
    val locationTypeId: kotlin.String? = null

)

