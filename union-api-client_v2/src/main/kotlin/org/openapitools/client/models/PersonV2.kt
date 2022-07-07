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
 * @param unionUser 
 * @param organization 
 * @param permissions 
 */

data class PersonV2 (

    @Json(name = "unionUser")
    val unionUser: UnionUserV2? = null,

    @Json(name = "organization")
    val organization: OrganizationV2? = null,

    @Json(name = "permissions")
    val permissions: kotlin.collections.Set<PermissionV2>? = null

)
