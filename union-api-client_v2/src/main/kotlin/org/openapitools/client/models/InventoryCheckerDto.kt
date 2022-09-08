package org.openapitools.client.models

import com.squareup.moshi.Json

data class InventoryCheckerDto(
    @Json(name = "inventoryId")
    val inventoryId: String,
    @Json(name = "employeeId")
    val employeeId: String,
    @Json(name = "extendedInventory")
    val extendedInventory: InventoryDtoV2?,
    @Json(name = "extendedEmployee")
    val extendedEmployee: EmployeeDtoV2?
)