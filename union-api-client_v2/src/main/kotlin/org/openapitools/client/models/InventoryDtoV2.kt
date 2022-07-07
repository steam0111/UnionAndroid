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
 * @param organizationId 
 * @param inventoryStateId 
 * @param inventoryTypeId 
 * @param dateInsert 
 * @param dateUpdate 
 * @param catalogItemName 
 * @param code 
 * @param creationDate 
 * @param internalNumber 
 * @param locationId 
 * @param molId 
 * @param name 
 * @param extendedOrganization 
 * @param extendedLocation 
 * @param extendedMol 
 * @param extendedInventoryState 
 * @param extendedInventoryType 
 * @param inventoryBaseId 
 * @param extendedInventoryBase 
 * @param comment 
 */

data class InventoryDtoV2 (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean,

    @Json(name = "version")
    val version: kotlin.Int?=null,

    @Json(name = "organizationId")
    val organizationId: kotlin.String,

    @Json(name = "inventoryStateId")
    val inventoryStateId: kotlin.String,

    @Json(name = "inventoryTypeId")
    val inventoryTypeId: kotlin.String,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "creationDate")
    val creationDate: kotlin.String? = null,

    @Json(name = "internalNumber")
    val internalNumber: kotlin.String? = null,

    @Json(name = "locationId")
    val locationId: kotlin.String? = null,

    @Json(name = "molId")
    val molId: kotlin.String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: OrganizationDtoV2? = null,

    @Json(name = "extendedLocation")
    val extendedLocation: LocationDtoV2? = null,

    @Json(name = "extendedMol")
    val extendedMol: EmployeeDtoV2? = null,

    @Json(name = "extendedInventoryState")
    val extendedInventoryState: InventoryStateDtoV2? = null,

    @Json(name = "extendedInventoryType")
    val extendedInventoryType: InventoryTypeDtoV2? = null,

    @Json(name = "inventoryBaseId")
    val inventoryBaseId: kotlin.String? = null,

    @Json(name = "extendedInventoryBase")
    val extendedInventoryBase: InventoryBaseDtoV2? = null,

    @Json(name = "comment")
    val comment: kotlin.String? = null

)
