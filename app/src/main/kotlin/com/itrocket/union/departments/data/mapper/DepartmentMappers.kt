package com.itrocket.union.departments.data.mapper

import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.itrocket.union.departments.domain.entity.DepartmentDomain

fun List<DepartmentSyncEntity>.map(): List<DepartmentDomain> = map {
    DepartmentDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        code = it.code.orEmpty()
    )
}