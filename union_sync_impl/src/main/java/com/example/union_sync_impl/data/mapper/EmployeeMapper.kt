package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_impl.entity.EmployeeDb
import org.openapitools.client.models.CustomEmployeeDto

fun CustomEmployeeDto.toEmployeeDb(): EmployeeDb {
    return EmployeeDb(
        id = id.orEmpty(),
        catalogItemName = catalogItemName.orEmpty(),
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        patronymic = patronymic.orEmpty(),
        number = number.orEmpty(),
        nfc = nfc,
        organizationId = organizationId
    )
}

fun EmployeeDb.toSyncEntity(): EmployeeSyncEntity {
    return EmployeeSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        organizationId = organizationId,
        firstname = firstname,
        lastname = lastname,
        patronymic = patronymic,
        number = number,
        nfc = nfc
    )
}