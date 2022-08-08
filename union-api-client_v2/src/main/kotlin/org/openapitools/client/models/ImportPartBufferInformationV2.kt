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

import org.openapitools.client.models.ImportPartInformationV2

import com.squareup.moshi.Json

/**
 * 
 *
 * @param importRequestsInformation 
 */

data class ImportPartBufferInformationV2 (

    @Json(name = "importRequestsInformation")
    val importRequestsInformation: kotlin.collections.List<ImportPartInformationV2>? = null

)

