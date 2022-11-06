package com.itrocket.union.inventoryChoose.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.inventoryChoose.domain.InventoryChooseActionType
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseComposeFragment

interface InventoryChooseStore :
    Store<InventoryChooseStore.Intent, InventoryChooseStore.State, InventoryChooseStore.Label> {

    sealed class Intent {
        data class OnActionClicked(val type: InventoryChooseActionType) : Intent()
    }

    data class State(
        val types: List<InventoryChooseActionType>
    )

    sealed class Label {
        data class GoBack(override val result: InventoryChooseResult?) : Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = InventoryChooseComposeFragment.INVENTORY_CHOOSE_RESULT_CODE

            override val resultLabel: String
                get() = InventoryChooseComposeFragment.INVENTORY_CHOOSE_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}