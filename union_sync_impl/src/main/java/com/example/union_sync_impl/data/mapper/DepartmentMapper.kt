package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.DepartmentDetailSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_impl.entity.DepartmentDb
import com.example.union_sync_impl.entity.FullDepartmentDb
import org.openapitools.client.models.CustomDepartmentDto
import org.openapitools.client.models.DepartmentDtoV2

fun CustomDepartmentDto.toDepartmentDb(): DepartmentDb {
    return DepartmentDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
    )
}

fun DepartmentDtoV2.toDepartmentDb(): DepartmentDb {
    return DepartmentDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
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

fun FullDepartmentDb.toDetailSyncEntity() = DepartmentDetailSyncEntity(
    department = departmentDb.toSyncEntity(),
    organization = organizationDb?.toSyncEntity()
)