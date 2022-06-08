package com.itrocket.union.inventory.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.inventory.InventoryModule.INVENTORY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragmentArgs

class InventoryComposeFragment :
    BaseComposeFragment<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label>(
        INVENTORY_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<InventoryComposeFragmentArgs>()

    override fun renderState(
        state: InventoryStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            /*InventoryScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoryStore.Intent.OnBackClicked)
                }
            )*/
        }
    }
}