package com.itrocket.union.inventoryContainer.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments

interface InventoryContainerStore :
    Store<InventoryContainerStore.Intent, InventoryContainerStore.State, InventoryContainerStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class ShowInventoryCreate(val inventoryCreate: InventoryCreateDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowInventoryCreate(val inventoryCreateArguments: InventoryCreateArguments) :
            Label()

    }
}