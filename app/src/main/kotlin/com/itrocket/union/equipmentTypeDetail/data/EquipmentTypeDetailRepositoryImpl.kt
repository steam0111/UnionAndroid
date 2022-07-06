package com.itrocket.union.equipmentTypeDetail.data

import com.example.union_sync_api.data.EquipmentTypeSyncApi
import com.itrocket.union.equipmentTypeDetail.data.mapper.toDomain
import com.itrocket.union.equipmentTypeDetail.domain.dependencies.EquipmentTypeDetailRepository
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain

class EquipmentTypeDetailRepositoryImpl(
    private val syncApi: EquipmentTypeSyncApi
) : EquipmentTypeDetailRepository {
    override suspend fun getEquipmentTypeById(id: String): EquipmentTypeDetailDomain {
        return syncApi.getEquipmentTypeDetail(id).toDomain()
    }
}