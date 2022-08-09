package com.itrocket.union.manual

import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity
import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity

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

fun NomenclatureGroupSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name,
    type = ManualType.NOMENCLATURE_GROUP
)

fun ReceptionItemCategorySyncEntity.toParam() = ParamDomain(
    id = id,
    value = name,
    type = ManualType.RECEPTION_CATEGORY
)

fun ActionBaseSyncEntity.toParam() = ParamDomain(
    id = id,
    value = name,
    type = ManualType.ACTION_BASE
)