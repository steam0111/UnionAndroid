package com.itrocket.union.inventory.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel

interface InventoryStore :
    Store<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val selectedPage: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}