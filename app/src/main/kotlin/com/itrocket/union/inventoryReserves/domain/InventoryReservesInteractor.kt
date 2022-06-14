package com.itrocket.union.inventoryReserves.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventoryReserves.domain.dependencies.InventoryReservesRepository
import com.itrocket.core.base.CoreDispatchers

class InventoryReservesInteractor(
    private val repository: InventoryReservesRepository,
    private val coreDispatchers: CoreDispatchers
) {

}