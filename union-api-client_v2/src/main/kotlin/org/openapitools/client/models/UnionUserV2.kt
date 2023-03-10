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

import org.openapitools.client.models.EmployeeV2
import org.openapitools.client.models.SyncItemV2
import org.openapitools.client.models.UnionRoleV2
import org.openapitools.client.models.UserPermissionV2
import org.openapitools.client.models.UserRoleV2

import com.squareup.moshi.Json

/**
 * 
 *
 * @param id 
 * @param deleted 
 * @param dateInsert 
 * @param dateUpdate 
 * @param userInserted 
 * @param userUpdated 
 * @param version 
 * @param catalogItemName 
 * @param code 
 * @param login 
 * @param password 
 * @param employee 
 * @param superuser 
 * @param userPermissions 
 * @param unionRole 
 * @param userRoles 
 * @param anotherSystemId 
 * @param cascadeItems 
 */

data class UnionUserV2 (

    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "deleted")
    val deleted: kotlin.Boolean? = null,

    @Json(name = "dateInsert")
    val dateInsert: kotlin.String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: kotlin.String? = null,

    @Json(name = "userInserted")
    val userInserted: kotlin.String? = null,

    @Json(name = "userUpdated")
    val userUpdated: kotlin.String? = null,

    @Json(name = "version")
    val version: kotlin.Int? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: kotlin.String? = null,

    @Json(name = "code")
    val code: kotlin.String? = null,

    @Json(name = "login")
    val login: kotlin.String? = null,

    @Json(name = "password")
    val password: kotlin.String? = null,

    @Json(name = "employee")
    val employee: EmployeeV2? = null,

    @Json(name = "superuser")
    val superuser: kotlin.Boolean? = null,

    @Json(name = "userPermissions")
    val userPermissions: kotlin.collections.Set<UserPermissionV2>? = null,

    @Json(name = "unionRole")
    val unionRole: UnionRoleV2? = null,

    @Json(name = "userRoles")
    val userRoles: kotlin.collections.Set<UserRoleV2>? = null,

    @Json(name = "anotherSystemId")
    val anotherSystemId: kotlin.String? = null,

    @Json(name = "cascadeItems")
    val cascadeItems: kotlin.collections.List<SyncItemV2>? = null

)

