package com.itrocket.union.inventories.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import kotlinx.coroutines.withContext

class InventoriesInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getInventories() = withContext(coreDispatchers.io) {
        repository.getInventories()
    }
}