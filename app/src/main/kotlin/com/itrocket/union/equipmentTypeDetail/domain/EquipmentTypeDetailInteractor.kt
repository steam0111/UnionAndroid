package com.itrocket.union.equipmentTypeDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypeDetail.domain.dependencies.EquipmentTypeDetailRepository
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain
import kotlinx.coroutines.withContext

class EquipmentTypeDetailInteractor(
    private val repository: EquipmentTypeDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getEquipmentTypeDetail(id: String): EquipmentTypeDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getEquipmentTypeById(id)
        }
}