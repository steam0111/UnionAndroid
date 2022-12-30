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
import java.math.BigDecimal

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
 * @param actionId
 * @param extendedAction
 * @param remainsId
 * @param extendedRemains
 * @param count
 */

data class ActionRemainsRecordDtoV2(

    @Json(name = "id")
    override val id: kotlin.String,

    @Json(name = "deleted")
    override val deleted: kotlin.Boolean,

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

    @Json(name = "actionId")
    val actionId: kotlin.String? = null,

    @Json(name = "extendedAction")
    val extendedAction: ActionDtoV2? = null,

    @Json(name = "remainsId")
    val remainsId: kotlin.String? = null,

    @Json(name = "extendedRemains")
    val extendedRemains: RemainsDtoV2? = null,

    @Json(name = "count")
    val count: BigDecimal? = null

) : DeletedItemDto

