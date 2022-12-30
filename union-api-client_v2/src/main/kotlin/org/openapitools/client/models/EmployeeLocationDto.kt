package org.openapitools.client.models

import com.squareup.moshi.Json

data class EmployeeLocationDto(
    @Json(name = "employeeId")
    val employeeId: String,
    @Json(name = "locationId")
    val locationId: String,
    @Json(name = "extendedLocation")
    val extendedLocation: LocationDtoV2? = null,
    @Json(name = "comment")
    val comment: String? = null,
    @Json(name = "mainWorkPlace")
    val mainWorkPlace: Boolean? = null,
    @Json(name = "deleted")
    override val deleted: Boolean
) : DeletedItemDto {
    override val id: String
        get() = employeeId
}