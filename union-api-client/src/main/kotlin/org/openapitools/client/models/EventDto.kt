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
 * @param unionUserId 
 * @param extendedUnionUser 
 * @param eventsTime 
 * @param move 
 * @param objectId 
 * @param entityModelId 
 * @param extendedEntityModel 
 */

data class EventDto (

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

    @Json(name = "unionUserId")
    val unionUserId: kotlin.String? = null,

    @Json(name = "extendedUnionUser")
    val extendedUnionUser: UnionUserDto? = null,

    @Json(name = "eventsTime")
    val eventsTime: kotlin.String? = null,

    @Json(name = "move")
    val move: kotlin.String? = null,

    @Json(name = "objectId")
    val objectId: kotlin.String? = null,

    @Json(name = "entityModelId")
    val entityModelId: kotlin.String? = null,

    @Json(name = "extendedEntityModel")
    val extendedEntityModel: EntityModel? = null

)

