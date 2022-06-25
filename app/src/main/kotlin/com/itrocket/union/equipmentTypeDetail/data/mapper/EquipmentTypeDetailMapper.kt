package com.itrocket.union.equipmentTypeDetail.data.mapper

import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain

fun EquipmentTypeSyncEntity.toDomain(): EquipmentTypeDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    code?.let {
        listInfo.add(ObjectInfoDomain(R.string.equipment_type_code, it))
    }

    return EquipmentTypeDetailDomain(listInfo)
}