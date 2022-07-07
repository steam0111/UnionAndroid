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
 * @param orderId 
 * @param itemsForCreate 
 * @param takenFrom 
 * @param takenIn 
 * @param actNumber 
 * @param storeTo 
 */

data class CreateReceptionRequestV2 (

    @Json(name = "orderId")
    val orderId: kotlin.String? = null,

    @Json(name = "itemsForCreate")
    val itemsForCreate: kotlin.collections.List<CreateReceptionRequestItemsV2>? = null,

    @Json(name = "takenFrom")
    val takenFrom: kotlin.String? = null,

    @Json(name = "takenIn")
    val takenIn: kotlin.String? = null,

    @Json(name = "actNumber")
    val actNumber: kotlin.String? = null,

    @Json(name = "storeTo")
    val storeTo: kotlin.String? = null

)

