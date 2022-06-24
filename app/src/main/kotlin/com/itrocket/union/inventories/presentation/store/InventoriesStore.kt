package com.itrocket.union.inventories.presentation.store

import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.inventories.presentation.view.InventoriesComposeFragmentDirections
import com.itrocket.union.inventoryContainer.presentation.store.InventoryContainerArguments
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments

interface InventoriesStore :
    Store<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        data class OnInventoryClicked(val inventory: InventoryCreateDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val inventories: List<InventoryCreateDomain> = listOf(),
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowSearch : Label()
        object ShowFilter : Label()
        data class ShowInventoryDetail(val inventory: InventoryCreateDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoriesComposeFragmentDirections.toInventoryContainer(
                    InventoryContainerArguments(inventory)
                )

        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}