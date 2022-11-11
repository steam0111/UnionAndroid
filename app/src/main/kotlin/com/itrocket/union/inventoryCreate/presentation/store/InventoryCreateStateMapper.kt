package com.itrocket.union.inventoryCreate.presentation.store

import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateUiState

fun InventoryCreateStore.State.toInventoryCreateUiState(): InventoryCreateUiState {
    val accountingObjects = when {
        isShowSearch && isHideFoundAccountingObjects -> searchAccountingObjects.filter { it.inventoryStatus != InventoryAccountingObjectStatus.FOUND }
        isShowSearch -> searchAccountingObjects
        isHideFoundAccountingObjects -> inventoryDocument.accountingObjects.filter { it.inventoryStatus != InventoryAccountingObjectStatus.FOUND }
        else -> inventoryDocument.accountingObjects
    }

    val canChangeInventory =
        inventoryDocument.inventoryStatus != InventoryStatus.COMPLETED && canUpdate
    val completeEnabled = canUpdate && !isLoading && !isCompleteLoading
    val dropEnabled = inventoryDocument.inventoryStatus == InventoryStatus.CREATED && canUpdate
    val showComplete = inventoryDocument.inventoryStatus == InventoryStatus.IN_PROGRESS && canComplete

    return InventoryCreateUiState(
        inventoryDocument = inventoryDocument,
        isHideFoundAccountingObjects = isHideFoundAccountingObjects,
        isAddNew = isAddNew,
        isLoading = isLoading,
        dialogType = dialogType,
        readingModeTab = readingModeTab,
        canUpdate = canUpdate,
        canComplete = canComplete,
        searchText = searchText,
        isShowSearch = isShowSearch,
        isDynamicSaveInventory = isDynamicSaveInventory,
        isCompleteLoading = isCompleteLoading,
        accountingObjects = accountingObjects,
        canChangeInventory = canChangeInventory,
        saveEnabled = canChangeInventory && !isLoading,
        completeEnabled = completeEnabled,
        dropEnabled = dropEnabled,
        showComplete = showComplete,
        accountingObjectCounter = accountingObjectCounter,
    )
}