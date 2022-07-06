package com.itrocket.union.equipmentTypeDetail.domain.dependencies

import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain

interface EquipmentTypeDetailRepository {
    suspend fun getEquipmentTypeById(id: String): EquipmentTypeDetailDomain
}