package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.FullEmployeeDb
import org.openapitools.client.models.CustomEmployeeDto
import org.openapitools.client.models.EmployeeDtoV2

fun CustomEmployeeDto.toEmployeeDb(): EmployeeDb {
    return EmployeeDb(
        id = id.orEmpty(),
        catalogItemName = catalogItemName.orEmpty(),
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        patronymic = patronymic.orEmpty(),
        number = number.orEmpty(),
        nfc = nfc,
        organizationId = organizationId,
        post = post,
        statusId = employeeStatusId,
        updateDate = System.currentTimeMillis()
    )
}

fun EmployeeDtoV2.toEmployeeDb(): EmployeeDb {
    return EmployeeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        patronymic = patronymic.orEmpty(),
        number = number.orEmpty(),
        nfc = nfc,
        organizationId = organizationId,
        post = post,
        statusId = employeeStatusId,
        updateDate = System.currentTimeMillis()
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
        nfc = nfc,
        post = post,
        statusId = statusId
    )
}

fun FullEmployeeDb.toDetailSyncEntity() = EmployeeDetailSyncEntity(
    employeeDb.toSyncEntity(),
    organizationDb?.toSyncEntity()
)