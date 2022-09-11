package com.itrocket.union.equipmentTypes.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.dependencies.EquipmentTypeRepository
import kotlinx.coroutines.withContext

class EquipmentTypeInteractor(
    private val repository: EquipmentTypeRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getEquipmentTypes(
        searchQuery: String = "",
        offset: Long? = null,
        limit: Long? = null
    ) = withContext(coreDispatchers.io) {
        repository.getEquipmentTypes(searchQuery, limit = limit, offset = offset)
    }
}