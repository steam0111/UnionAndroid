package com.itrocket.union.inventories.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class InventoriesInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getInventories(searchQuery: String = ""): Flow<List<InventoryCreateDomain>> = withContext(coreDispatchers.io) {
        repository.getInventories(searchQuery)
    }
}