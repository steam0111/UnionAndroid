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
 * @param login 
 * @param password 
 * @param superuser 
 * @param roleId 
 * @param employeeId 
 * @param extendedEmployee 
 * @param extendedPermissions 
 * @param extendedRole 
 * @param extendedRoles 
 */

data class UnionUserDto (

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

    @Json(name = "login")
    val login: kotlin.String? = null,

    @Json(name = "password")
    val password: kotlin.String? = null,

    @Json(name = "superuser")
    val superuser: kotlin.Boolean? = null,

    @Json(name = "roleId")
    val roleId: kotlin.String? = null,

    @Json(name = "employeeId")
    val employeeId: kotlin.String? = null,

    @Json(name = "extendedEmployee")
    val extendedEmployee: EmployeeDto? = null,

    @Json(name = "extendedPermissions")
    val extendedPermissions: kotlin.collections.List<PermissionDto>? = null,

    @Json(name = "extendedRole")
    val extendedRole: UnionRoleDto? = null,

    @Json(name = "extendedRoles")
    val extendedRoles: kotlin.collections.List<UnionRoleDto>? = null

)

