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
 * Кастомизировано и добавлено в игнор, обновляться при генерации апи не будет !!!
 *
 * @param extended 
 * @param errorCode 
 * @param list 
 * @param page 
 * @param propertySize 
 * @param total 
 */

data class GetAllResponse<T> (

    @Json(name = "extended")
    val extended: kotlin.Boolean? = null,

    @Json(name = "errorCode")
    val errorCode: kotlin.String? = null,

    @Json(name = "list")
    val list: List<T>? = null,

    @Json(name = "page")
    val page: kotlin.Long? = null,

    @Json(name = "size")
    val propertySize: kotlin.Long? = null,

    @Json(name = "total")
    val total: kotlin.Long? = null

)

