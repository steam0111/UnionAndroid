package com.itrocket.union.inventory.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.inventory.InventoryModule.INVENTORY_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.location.presentation.view.LocationComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment

class InventoryComposeFragment :
    BaseComposeFragment<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label>(
        INVENTORY_VIEW_MODEL_QUALIFIER
    ) {

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    accept(
                        InventoryStore.Intent.OnParamsChanged(
                            (it as SelectParamsResult?)?.params ?: return@FragmentResult
                        )
                    )
                }
            ),
            FragmentResult(
                resultCode = LocationComposeFragment.LOCATION_RESULT_CODE,
                resultLabel = LocationComposeFragment.LOCATION_RESULT,
                resultAction = {
                    accept(
                        InventoryStore.Intent.OnLocationChanged(
                            it as LocationResult? ?: return@FragmentResult
                        )
                    )
                }
            )
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
                }
            )
        }
    }
}