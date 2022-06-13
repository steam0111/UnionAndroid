package com.itrocket.union.inventoryCreate.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.entity.InventoryDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

interface InventoryCreateStore :
    Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> {

    sealed class Intent {
        data class OnAccountingObjectClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnHideFoundAccountingObjectClicked : Intent()
        object OnAddNewClicked : Intent()
        object OnSaveClicked : Intent()
        object OnReadingClicked : Intent()
    }

    data class State(
        val inventoryDocument: InventoryCreateDomain,
        val accountingObjects: List<AccountingObjectDomain>,
        val isHideFoundAccountingObjects: Boolean = false,
        val newAccountingObjects: List<AccountingObjectDomain> = listOf(),
        val isAddNew: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        object ShowChangeStatus : Label()
        object ShowNewAccountingObjectDetail : Label()
        object ShowLeaveWithoutSave : Label()
        object ShowReading : Label()
    }
}