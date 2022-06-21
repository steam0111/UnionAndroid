package com.itrocket.union.inventories.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.inventories.InventoriesModule.INVENTORIES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventories.presentation.store.InventoriesStore

class InventoriesComposeFragment :
    BaseComposeFragment<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label>(
        INVENTORIES_VIEW_MODEL_QUALIFIER
    ) {

    override fun renderState(
        state: InventoriesStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoriesScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoriesStore.Intent.OnBackClicked)
                },
                onSearchClickListener = {
                    accept(InventoriesStore.Intent.OnSearchClicked)
                },
                onFilterClickListener = {
                    accept(InventoriesStore.Intent.OnFilterClicked)
                },
                onInventoryClickListener = {
                    accept(InventoriesStore.Intent.OnInventoryClicked(it))
                }
            )
        }
    }
}