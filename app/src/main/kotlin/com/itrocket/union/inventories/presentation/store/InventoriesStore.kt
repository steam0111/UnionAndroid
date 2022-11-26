package com.itrocket.union.inventories.presentation.store

import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.inventories.presentation.view.InventoriesComposeFragmentDirections
import com.itrocket.union.inventoryContainer.domain.InventoryContainerType
import com.itrocket.union.inventoryContainer.presentation.store.InventoryContainerArguments
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ParamDomain

interface InventoriesStore :
    Store<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label> {

    sealed class Intent {
        class OnFilterResult(val params: List<ParamDomain>) : Intent()
        object OnBackClicked : Intent()
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        data class OnInventoryClicked(val inventory: InventoryCreateDomain) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnLoadNext : Intent()
        class OnInventoryResult(val changed: Boolean) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val inventories: List<InventoryCreateDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = "",
        val params: List<ParamDomain>? = null,
        val isListEndReached: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowInventoryDetail(
            val inventory: InventoryCreateDomain,
            val type: InventoryContainerType
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoriesComposeFragmentDirections.toInventoryContainer(
                    InventoryContainerArguments(inventory, type)
                )

        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoriesComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.Inventories
                    )
                )
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}