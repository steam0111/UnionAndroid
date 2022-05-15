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
 * @param dateInsert 
 * @param dateUpdate 
 * @param version 
 * @param catalogItemName 
 * @param unionRole 
 * @param permission 
 * @param cascadeItems 
 */

data class RolePermission (

    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean? = null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "version")
    val version: kotlin.Int? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "unionRole")
    val unionRole: UnionRole? = null,

    @Json(name = "permission")
    val permission: Permission? = null,

    @Json(name = "cascadeItems")
    val cascadeItems: kotlin.collections.List<SyncItem>? = null

)

