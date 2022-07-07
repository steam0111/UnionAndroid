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
 * @param date 
 * @param orderId 
 * @param extendedOrder 
 * @param statusId 
 * @param extendedStatus 
 * @param takenFrom 
 * @param takenIn 
 * @param actNumber 
 * @param storeTo 
 */

data class ReceptionDtoV2 (

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

    @Json(name = "date")
    val date: kotlin.String? = null,

    @Json(name = "orderId")
    val orderId: kotlin.String? = null,

    @Json(name = "extendedOrder")
    val extendedOrder: OrderDtoV2? = null,

    @Json(name = "statusId")
    val statusId: kotlin.String? = null,

    @Json(name = "extendedStatus")
    val extendedStatus: ActionStatusDtoV2? = null,

    @Json(name = "takenFrom")
    val takenFrom: kotlin.String? = null,

    @Json(name = "takenIn")
    val takenIn: kotlin.String? = null,

    @Json(name = "actNumber")
    val actNumber: kotlin.String? = null,

    @Json(name = "storeTo")
    val storeTo: kotlin.String? = null

)
