package com.itrocket.union.inventory.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.core.base.CoreDispatchers

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {

}