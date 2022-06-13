package com.itrocket.union.inventoryCreate.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.union.inventoryCreate.InventoryCreateModule.INVENTORYCREATE_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.base.AppInsets
import androidx.navigation.fragment.navArgs
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.inventoryCreate.presentation.view.InventoryCreateComposeFragmentArgs
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectResult
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment

class InventoryCreateComposeFragment :
    BaseComposeFragment<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label>(
        INVENTORYCREATE_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<InventoryCreateComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = NewAccountingObjectComposeFragment.NEW_ACCOUNTING_OBJECT_RESULT_CODE,
                resultLabel = NewAccountingObjectComposeFragment.NEW_ACCOUNTING_OBJECT_RESULT,
                resultAction = {
                    (it as NewAccountingObjectResult?)?.accountingObject?.let {
                        accept(
                            InventoryCreateStore.Intent.OnNewAccountingObjectAdded(it)
                        )
                    }
                }
            )
        )

    override fun renderState(
        state: InventoryCreateStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryCreateScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoryCreateStore.Intent.OnBackClicked)
                },
                onAccountingObjectClickListener = {
                    accept(InventoryCreateStore.Intent.OnAccountingObjectClicked(it))
                },
                onSaveClickListener = {
                    accept(InventoryCreateStore.Intent.OnSaveClicked)
                },
                onAddNewChanged = {
                    accept(InventoryCreateStore.Intent.OnAddNewClicked)
                },
                onDropClickListener = {
                    accept(InventoryCreateStore.Intent.OnDropClicked)
                },
                onHideFoundAccountingObjectChanged = {
                    accept(InventoryCreateStore.Intent.OnHideFoundAccountingObjectClicked)
                },
                onReadingClickListener = {
                    accept(InventoryCreateStore.Intent.OnReadingClicked)
                }
            )
        }
    }
}