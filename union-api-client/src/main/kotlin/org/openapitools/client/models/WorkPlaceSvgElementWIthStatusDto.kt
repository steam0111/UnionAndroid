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
 * @param workPlaceSvgElement 
 * @param workPlaceSvgStatus 
 */

data class WorkPlaceSvgElementWIthStatusDto (

    @Json(name = "workPlaceSvgElement")
    val workPlaceSvgElement: WorkPlaceSvgElementDto? = null,

    @Json(name = "workPlaceSvgStatus")
    val workPlaceSvgStatus: WorkPlaceSvgElementWIthStatusDto.WorkPlaceSvgStatus? = null

) {

    /**
     * 
     *
     * Values: bUSY,fREE,bUSYBYACCOUNTINGOBJECTWITHOUTOWNER
     */
    enum class WorkPlaceSvgStatus(val value: kotlin.String) {
        @Json(name = "BUSY") bUSY("BUSY"),
        @Json(name = "FREE") fREE("FREE"),
        @Json(name = "BUSY_BY_ACCOUNTING_OBJECT_WITHOUT_OWNER") bUSYBYACCOUNTINGOBJECTWITHOUTOWNER("BUSY_BY_ACCOUNTING_OBJECT_WITHOUT_OWNER");
    }
}
