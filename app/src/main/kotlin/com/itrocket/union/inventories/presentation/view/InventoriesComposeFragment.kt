package com.itrocket.union.inventories.presentation.view

import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.inventories.InventoriesModule.INVENTORIES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.inventory.presentation.store.InventoryResult
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_CODE
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_LABEL
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

class InventoriesComposeFragment :
    BaseComposeFragment<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label>(
        INVENTORIES_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(InventoriesStore.Intent.OnBackClicked)
            }
        }
    }

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(InventoriesStore.Intent.OnFilterResult(it))
                    }
                }
            ),
            FragmentResult(
                resultCode = INVENTORY_RESULT_CODE,
                resultLabel = INVENTORY_RESULT_LABEL,
                resultAction = {
                    (it as InventoryResult?)?.let {
                        accept(InventoriesStore.Intent.OnInventoryResult(it.dataChanged))
                    }
                }
            )
        )

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
                },
                onSearchTextChanged = {
                    accept(InventoriesStore.Intent.OnSearchTextChanged(it))
                },
                onLoadNext = {
                    accept(InventoriesStore.Intent.OnLoadNext)
                }
            )
        }
    }
}