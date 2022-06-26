package com.itrocket.union.equipmentTypes.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.dependencies.EquipmentTypeRepository
import kotlinx.coroutines.withContext

class EquipmentTypeInteractor(
    private val repository: EquipmentTypeRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getEquipmentTypes(searchQuery: String = "") = withContext(coreDispatchers.io) {
        repository.getEquipmentTypes()
    }
}