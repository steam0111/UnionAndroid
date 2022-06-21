package org.openapitools.client.models

import com.squareup.moshi.Json

data class Commissioning (

    @Json(name = "id")
    val id: String,

    @Json(name = "deleted")
    val deleted: Boolean,

    @Json(name = "version")
    val version: Int? = null,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "date")
    val date: String? = null,

    @Json(name = "organizationId")
    val organizationId: String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: CustomOrganizationDto? = null,

    @Json(name = "responsibleId")
    val responsibleId: String? = null,

    @Json(name = "extendedResponsible")
    val extendedResponsible: Employee? = null,

    @Json(name = "statusId")
    val statusId: String? = null,

    @Json(name = "extendedStatus")
    val extendedStatus: ActionStatusDto? = null

)