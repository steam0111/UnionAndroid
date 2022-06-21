package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_impl.entity.DepartmentDb
import org.openapitools.client.models.CustomDepartmentDto
import org.openapitools.client.models.DepartmentDto

fun CustomDepartmentDto.toDepartmentDb(): DepartmentDb {
    return DepartmentDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code
    )
}

fun DepartmentDb.toSyncEntity(): DepartmentSyncEntity {
    return DepartmentSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        organizationId = organizationId,
        name = name,
        code = code
    )
}