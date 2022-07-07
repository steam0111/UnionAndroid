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
 * @param offset 
 * @param sort 
 * @param pageNumber 
 * @param pageSize 
 * @param paged 
 * @param unpaged 
 */

data class PageableV2 (

    @Json(name = "offset")
    val offset: kotlin.Long? = null,

    @Json(name = "sort")
    val sort: SortV2? = null,

    @Json(name = "pageNumber")
    val pageNumber: kotlin.Int? = null,

    @Json(name = "pageSize")
    val pageSize: kotlin.Int? = null,

    @Json(name = "paged")
    val paged: kotlin.Boolean? = null,

    @Json(name = "unpaged")
    val unpaged: kotlin.Boolean? = null

)
