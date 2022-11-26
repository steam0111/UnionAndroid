package com.itrocket.union.inventories.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import kotlinx.coroutines.withContext

class InventoriesInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getInventories(
        searchQuery: String = "",
        params: List<ParamDomain>?,
        offset: Long,
        limit: Long? = null
    ): List<InventoryCreateDomain> = withContext(coreDispatchers.io) {
        repository.getInventories(
            textQuery = searchQuery,
            params = params,
            offset = offset,
            limit = limit
        )
    }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            StructuralParamDomain(manualType = ManualType.STRUCTURAL),
            ParamDomain(type = ManualType.MOL, value = ""),
            ParamDomain(type = ManualType.INVENTORY_CODE)
        )
    }
}