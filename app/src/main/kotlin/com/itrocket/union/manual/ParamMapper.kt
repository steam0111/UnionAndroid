package com.itrocket.union.manual

import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity

fun OrganizationSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name,
    type = ManualType.ORGANIZATION
)

fun EmployeeSyncEntity.toParam(type: ManualType) = ParamDomain(
    id = id,
    value = "$firstname $lastname",
    type = type
)