package com.itrocket.union.equipmentTypes.domain.dependencies

import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import kotlinx.coroutines.flow.Flow

interface EquipmentTypeRepository {
    suspend fun getEquipmentTypes(textQuery: String?): Flow<List<EquipmentTypesDomain>>
}