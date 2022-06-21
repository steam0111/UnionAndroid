package org.openapitools.client.models

import com.squareup.moshi.Json

data class CustomEmployeeDto(
    @Json(name = "id")
    val id: String?,

    @Json(name = "deleted")
    val deleted: Boolean?,

    @Json(name = "version")
    val version: Int?,

    @Json(name = "dateInsert")
    val dateInsert: String? = null,

    @Json(name = "dateUpdate")
    val dateUpdate: String? = null,

    @Json(name = "catalogItemName")
    val catalogItemName: String? = null,

    @Json(name = "firstname")
    val firstname: String? = null,

    @Json(name = "lastname")
    val lastname: String? = null,

    @Json(name = "patronymic")
    val patronymic: String? = null,

    @Json(name = "birthdate")
    val birthdate: String? = null,

    @Json(name = "organizationId")
    val organizationId: String? = null,

    @Json(name = "extendedOrganization")
    val extendedOrganization: CustomOrganizationDto? = null,

    @Json(name = "post")
    val post: String? = null,

    @Json(name = "number")
    val number: String? = null,

    @Json(name = "card")
    val card: String? = null,

    @Json(name = "nfc")
    val nfc: String? = null,

    @Json(name = "employeeStatusId")
    val employeeStatusId: String? = null

)