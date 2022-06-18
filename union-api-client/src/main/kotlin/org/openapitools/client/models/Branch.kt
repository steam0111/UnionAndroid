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
 * @param name
 * @param code
 * @param organizationId
 * @param extendedOrganization
 */

data class Branch(

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean? = null,

    @Json(name = "version")
    val version: Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "code")
    val code: String? = null,

    @Json(name = "organizationId")
    val organizationId: String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: CustomOrganizationDto? = null

)
