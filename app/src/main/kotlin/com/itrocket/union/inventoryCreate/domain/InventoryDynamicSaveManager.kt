package com.itrocket.union.inventoryCreate.domain

import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class InventoryDynamicSaveManager(private val inventoryCreateInteractor: InventoryCreateInteractor) {

    private val inventorySaveFlow = MutableSharedFlow<InventoryCreateDomain>(
        onBufferOverflow = BufferOverflow.DROP_LATEST,
        extraBufferCapacity = 1,
    )

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.IO + Job())
    }

    private var inventorySaveJob: Job? = null

    fun sendInventoryDomain(inventoryDomain: InventoryCreateDomain) {
        coroutineScope.launch {
            inventorySaveFlow.emit(inventoryDomain)
        }
    }

    fun subscribeInventorySave() {
        inventorySaveJob = coroutineScope.launch {
            inventorySaveFlow
                .debounce(INVENTORY_COLLECT_DELAY)
                .distinctUntilChanged()
                .collect {
                    inventoryCreateInteractor.saveInventoryDocument(it, it.accountingObjects, it.nomenclatureRecords)
                }
        }
    }

    fun cancel() {
        inventorySaveJob?.cancel()
    }

    companion object {
        private const val INVENTORY_COLLECT_DELAY = 100L
    }
}