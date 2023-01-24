package com.itrocket.union.inventory.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailResult
import com.itrocket.union.accountingObjectDetail.presentation.view.AccountingObjectDetailComposeFragment
import com.itrocket.union.inventory.InventoryModule.INVENTORY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventoryContainer.presentation.view.InventoryCreateClickHandler
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment
import com.itrocket.union.utils.fragment.ChildBackPressedHandler

class InventoryComposeFragment :
    BaseComposeFragment<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label>(
        INVENTORY_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(InventoryStore.Intent.OnBackClicked)
            }
        }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            InventoryStore.Intent.OnParamsChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = AccountingObjectDetailComposeFragment.ACCOUNTING_OBJECT_DETAIL_RESULT_CODE,
                resultLabel = AccountingObjectDetailComposeFragment.ACCOUNTING_OBJECT_DETAIL_RESULT_LABEL,
                resultAction = {
                    (it as AccountingObjectDetailResult?)?.let {
                        accept(
                            InventoryStore.Intent.OnAccountingObjectChanged(it.accountingObject)
                        )
                    }
                }
            ),
        )

    override fun renderState(
        state: InventoryStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoryStore.Intent.OnBackClicked)
                },
                onDropClickListener = {
                    accept(InventoryStore.Intent.OnDropClicked)
                },
                onParamClickListener = {
                    accept(InventoryStore.Intent.OnParamClicked(it))
                },
                onInventoryCreateClickListener = {
                    accept(InventoryStore.Intent.OnCreateDocumentClicked)
                },
                onPageChanged = {
                    accept(InventoryStore.Intent.OnSelectPage(it))
                },
                onParamCrossClickListener = {
                    accept(InventoryStore.Intent.OnParamCrossClicked(it))
                },
                onSaveClickListener = {
                    accept(InventoryStore.Intent.OnSaveClicked)
                },
                onInWorkClickListener = {
                    accept(InventoryStore.Intent.OnInWorkClicked)
                },
                onInWorkConfirmClickListener = {
                    accept(InventoryStore.Intent.OnInWorkConfirmed)
                },
                onInWorkDismissClickListener = {
                    accept(InventoryStore.Intent.OnInWorkDismissed)
                },
                onSaveConfirmClickListener = {
                    accept(InventoryStore.Intent.OnSaveConfirmed)
                },
                onSaveDismissClickListener = {
                    accept(InventoryStore.Intent.OnSaveDismissed)
                },
                onAccountingObjectClickListener = {
                    accept(InventoryStore.Intent.OnAccountingObjectClicked(it))
                },
                onAlertDismissClickListener = {
                    accept(InventoryStore.Intent.OnAlertDismissed)
                },
                onExitConfirmClickListener = {
                    accept(InventoryStore.Intent.OnExitConfirmed)
                },
                onDropConfirmedClickListener = {
                    accept(InventoryStore.Intent.OnDropConfirmed)
                },
                onInventoryNomenclatureClickListener = {
                    accept(InventoryStore.Intent.OnInventoryNomenclatureClicked(it))
                }
            )
        }
    }

    override fun handleLabel(label: InventoryStore.Label) {
        super.handleLabel(label)
        when (label) {
            is InventoryStore.Label.GoBack -> (parentFragment as? ChildBackPressedHandler)?.onChildBackPressed()
            is InventoryStore.Label.ShowCreateInventory -> (parentFragment as? InventoryCreateClickHandler)?.onInventoryCreateClicked(
                label.inventoryCreate
            )
            else -> {}
        }

    }

    companion object {
        const val INVENTORY_ARGUMENT = "inventory argument"
        const val INVENTORY_RESULT_CODE = "inventory result code"
        const val INVENTORY_RESULT_LABEL = "inventory result label"
    }
}