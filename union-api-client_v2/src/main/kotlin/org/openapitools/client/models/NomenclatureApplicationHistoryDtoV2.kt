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

import org.openapitools.client.models.EmployeeDtoV2
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.NomenclatureApplicationDtoV2

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
 * @param nomenclatureApplicationId 
 * @param extendedNomenclatureApplication 
 * @param date 
 * @param changerAuthorId 
 * @param extendedChangeAuthor 
 * @param statusId 
 * @param extendedStatus 
 * @param comment 
 */

data class NomenclatureApplicationHistoryDtoV2 (

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

    @Json(name = "nomenclatureApplicationId")
    val nomenclatureApplicationId: kotlin.String? = null,

    @Json(name = "extendedNomenclatureApplication")
    val extendedNomenclatureApplication: NomenclatureApplicationDtoV2? = null,

    @Json(name = "date")
    val date: kotlin.String? = null,

    @Json(name = "changerAuthorId")
    val changerAuthorId: kotlin.String? = null,

    @Json(name = "extendedChangeAuthor")
    val extendedChangeAuthor: EmployeeDtoV2? = null,

    @Json(name = "statusId")
    val statusId: kotlin.String? = null,

    @Json(name = "extendedStatus")
    val extendedStatus: EnumDtoV2? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null

)

