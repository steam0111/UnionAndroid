package org.openapitools.client.models

import com.squareup.moshi.Json

/**
 *
 *
 * @param id
 * @param deleted
 * @param version
 * @param name
 * @param dateInsert
 * @param dateUpdate
 * @param catalogItemName
 * @param organizationId
 * @param inn
 * @param kpp
 * @param actualAddress
 * @param legalAddress
 * @param comment
 * @param premises
 * @param floor
 * @param transit
 * @param rfidValue
 * @param nfcValue
 * @param barcodeValue
 * @param parentId
 * @param extendedParent
 * @param locationTypeId
 */

data class Location (

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean,

    @Json(name = "version")
    val version: Int? = null,

    @Json(name = "name")
    val name: String,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "organizationId")
    val organizationId: String? = null,

    @Json(name = "inn")
    val inn: String? = null,

    @Json(name = "kpp")
    val kpp: String? = null,

    @Json(name = "actualAddress")
    val actualAddress: String? = null,

    @Json(name = "legalAddress")
    val legalAddress: String? = null,

    @Json(name = "comment")
    val comment: String? = null,

    @Json(name = "premises")
    val premises: String? = null,

    @Json(name = "floor")
    val floor: String? = null,

    @Json(name = "transit")
    val transit: Boolean? = null,

    @Json(name = "rfidValue")
    val rfidValue: String? = null,

    @Json(name = "nfcValue")
    val nfcValue: String? = null,

    @Json(name = "barcodeValue")
    val barcodeValue: String? = null,

    @Json(name = "parentId")
    val parentId: String? = null,

    @Json(name = "extendedParent")
    val extendedParent: Location? = null,

    @Json(name = "locationTypeId")
    val locationTypeId: String? = null

)
