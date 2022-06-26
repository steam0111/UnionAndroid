package com.itrocket.union.manual

import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity
import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_api.entity.ProducerSyncEntity

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

fun AccountingObjectStatusSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name.orEmpty(),
    type = ManualType.STATUS
)

fun EquipmentTypeSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name.orEmpty(),
    type = ManualType.STATUS
)

fun DepartmentSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name.orEmpty(),
    type = ManualType.STATUS
)

fun ProducerSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name.orEmpty(),
    type = ManualType.STATUS
)

fun CounterpartySyncEntity.toParam() = ParamDomain(
    id = id,
    value = name.orEmpty(),
    type = ManualType.STATUS
)