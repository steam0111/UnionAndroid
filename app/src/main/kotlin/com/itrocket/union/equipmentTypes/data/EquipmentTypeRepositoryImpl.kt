package com.itrocket.union.equipmentTypes.data

import com.example.union_sync_api.data.EquipmentTypesSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.dependencies.EquipmentTypeRepository
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.itrocket.union.equipmentTypes.data.mapper.map
import kotlinx.coroutines.flow.map

class EquipmentTypeRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val equipmentTypesSyncApi: EquipmentTypesSyncApi
) : EquipmentTypeRepository {

    override suspend fun getEquipmentTypes(): Flow<List<EquipmentTypesDomain>> {
        return withContext(coreDispatchers.io) {
            equipmentTypesSyncApi.getEquipmentTypes().map { it.map() }
        }
    }
}