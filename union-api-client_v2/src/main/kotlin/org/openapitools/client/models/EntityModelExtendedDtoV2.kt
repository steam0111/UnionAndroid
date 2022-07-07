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
 * @param name 
 * @param extendedScopes 
 * @param extendedRestActions 
 * @param extendedEntityModelType 
 * @param endpoint 
 */

data class EntityModelExtendedDtoV2 (

    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "extendedScopes")
    val extendedScopes: kotlin.collections.List<ScopeV2>? = null,

    @Json(name = "extendedRestActions")
    val extendedRestActions: kotlin.collections.List<RestActionDtoV2>? = null,

    @Json(name = "extendedEntityModelType")
    val extendedEntityModelType: EntityModelTypeDtoV2? = null,

    @Json(name = "endpoint")
    val endpoint: kotlin.String? = null

)

