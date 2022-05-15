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
 * @param model 
 * @param action 
 * @param permissionScopes 
 * @param userPermissions 
 * @param rolePermissions 
 * @param cascadeItems 
 */

data class Permission (

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

    @Json(name = "model")
    val model: kotlin.String? = null,

    @Json(name = "action")
    val action: kotlin.String? = null,

    @Json(name = "permissionScopes")
    val permissionScopes: kotlin.collections.Set<PermissionScope>? = null,

    @Json(name = "userPermissions")
    val userPermissions: kotlin.collections.Set<UserPermission>? = null,

    @Json(name = "rolePermissions")
    val rolePermissions: kotlin.collections.Set<RolePermission>? = null,

    @Json(name = "cascadeItems")
    val cascadeItems: kotlin.collections.List<SyncItem>? = null

)

