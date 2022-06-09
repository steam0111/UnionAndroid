package com.itrocket.union.inventoryCreate.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.core.base.CoreDispatchers

class InventoryCreateInteractor(
    private val repository: InventoryCreateRepository,
    private val coreDispatchers: CoreDispatchers
) {

}