package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.FullEmployeeDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.EmployeeDtoV2

fun EmployeeDtoV2.toEmployeeDb(): EmployeeDb {
    return EmployeeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        firstname = firstname.orEmpty(),
        lastname = lastname.orEmpty(),
        patronymic = patronymic.orEmpty(),
        number = number.orEmpty(),
        nfc = nfc,
        structuralId = structuralUnitId,
        post = post,
        statusId = employeeStatusId,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userInserted = userInserted,
        userUpdated = userUpdated,
        cancel = deleted
    )
}

fun EmployeeDb.toSyncEntity(): EmployeeSyncEntity {
    return EmployeeSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        structuralId = structuralId,
        firstname = firstname,
        lastname = lastname,
        patronymic = patronymic,
        number = number,
        nfc = nfc,
        post = post,
        statusId = statusId,
        userInserted = userInserted,
        userUpdated = userUpdated,
        dateInsert = insertDate,
        updateDate = updateDate
    )
}

fun FullEmployeeDb.toDetailSyncEntity(
    balanceUnits: List<StructuralSyncEntity>?,
    structurals: List<StructuralSyncEntity>?,
    employeeStatusSyncEntity: EnumSyncEntity?
) =
    EmployeeDetailSyncEntity(
        employeeDb.toSyncEntity(),
        structurals,
        balanceUnits,
        employeeStatusSyncEntity
    )