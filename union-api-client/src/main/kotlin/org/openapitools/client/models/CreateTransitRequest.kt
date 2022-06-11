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
 * @param responsibleId 
 * @param receivingId 
 * @param locationToId 
 * @param locationFromId 
 * @param vehicleId 
 * @param accountingObjectRecords 
 * @param remainsRecords 
 */

data class CreateTransitRequest (

    @Json(name = "responsibleId")
    val responsibleId: kotlin.String? = null,

    @Json(name = "receivingId")
    val receivingId: kotlin.String? = null,

    @Json(name = "locationToId")
    val locationToId: kotlin.String? = null,

    @Json(name = "locationFromId")
    val locationFromId: kotlin.String? = null,

    @Json(name = "vehicleId")
    val vehicleId: kotlin.String? = null,

    @Json(name = "accountingObjectRecords")
    val accountingObjectRecords: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "remainsRecords")
    val remainsRecords: kotlin.collections.List<CreateTransitRequestRemainsItem>? = null

)

