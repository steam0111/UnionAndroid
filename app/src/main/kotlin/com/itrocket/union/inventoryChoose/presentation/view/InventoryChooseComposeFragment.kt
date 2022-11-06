package com.itrocket.union.inventoryChoose.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.inventoryChoose.InventoryChooseModule
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseStore

class InventoryChooseComposeFragment :
    BaseComposeBottomSheet<InventoryChooseStore.Intent, InventoryChooseStore.State, InventoryChooseStore.Label>(
        InventoryChooseModule.INVENTORYCHOOSE_VIEW_MODEL_QUALIFIER
    ) {
    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: InventoryChooseStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryChooseScreen(
                state = state,
                appInsets = appInsets,
                onActionClickListener = {
                    accept(InventoryChooseStore.Intent.OnActionClicked(it))
                }
            )
        }
    }

    companion object {
        const val INVENTORY_CHOOSE_ARGUMENT = "inventory choose argument"
        const val INVENTORY_CHOOSE_RESULT_CODE = "inventory choose result code"
        const val INVENTORY_CHOOSE_RESULT_LABEL = "inventory choose result label"
    }
}