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

data class Person (

    @Json(name = "unionUser")
    val unionUser: UnionUser? = null,

    @Json(name = "organization")
    val organization: CustomOrganizationDto? = null,

    @Json(name = "permissions")
    val permissions: kotlin.collections.Set<Permission>? = null

)

