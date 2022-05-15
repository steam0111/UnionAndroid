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
 * @param accessToken 
 * @param refreshToken 
 * @param userLogin 
 */

data class AuthJwtResponse (

    @Json(name = "accessToken")
    val accessToken: kotlin.String? = null,

    @Json(name = "refreshToken")
    val refreshToken: kotlin.String? = null,

    @Json(name = "userLogin")
    val userLogin: kotlin.String? = null

)

