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
 * @param accountingObjectId 
 * @param extendedAccountingObject 
 * @param characteristicId 
 * @param extendedCharacteristic 
 * @param `value` 
 */

data class AccountingObjectCharacteristicValueDtoV2 (

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

    @Json(name = "accountingObjectId")
    val accountingObjectId: kotlin.String? = null,

    @Json(name = "extendedAccountingObject")
    val extendedAccountingObject: AccountingObjectDtoV2? = null,

    @Json(name = "characteristicId")
    val characteristicId: kotlin.String? = null,

    @Json(name = "extendedCharacteristic")
    val extendedCharacteristic: CharacteristicDtoV2? = null,

    @Json(name = "value")
    val `value`: kotlin.String? = null

)
