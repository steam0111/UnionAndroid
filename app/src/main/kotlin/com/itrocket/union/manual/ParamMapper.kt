package com.itrocket.union.manual

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity

fun EmployeeSyncEntity.toParam(type: ManualType) = ParamDomain(
    id = id,
    value = fullName,
    type = type
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

fun EnumSyncEntity.toParam(type: ManualType) = ParamDomain(
    id = id,
    value = name,
    type = type
)

fun String.toParam(type: ManualType) = ParamDomain(
    value = this,
    type = type,
    id = this
)