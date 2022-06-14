package com.itrocket.union.inventoryReserves.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventoryReserves.InventoryReservesModule.INVENTORYRESERVES_VIEW_MODEL_QUALIFIER
import com.itrocket.union.inventoryReserves.presentation.store.InventoryReservesStore
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.location.presentation.view.LocationComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.selectParams.presentation.view.SelectParamsComposeFragment

class InventoryReservesComposeFragment :
    BaseComposeFragment<InventoryReservesStore.Intent, InventoryReservesStore.State, InventoryReservesStore.Label>(
        INVENTORYRESERVES_VIEW_MODEL_QUALIFIER
    ) {
    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = SelectParamsComposeFragment.SELECT_PARAMS_RESULT_CODE,
                resultLabel = SelectParamsComposeFragment.SELECT_PARAMS_RESULT,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(
                            InventoryReservesStore.Intent.OnParamsChanged(it)
                        )
                    }
                }
            ),
            FragmentResult(
                resultCode = LocationComposeFragment.LOCATION_RESULT_CODE,
                resultLabel = LocationComposeFragment.LOCATION_RESULT,
                resultAction = {
                    (it as LocationResult?)?.let {
                        accept(
                            InventoryReservesStore.Intent.OnLocationChanged(it)
                        )
                    }
                }
            )
        )


    override fun renderState(
        state: InventoryReservesStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            InventoryReservesScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(InventoryReservesStore.Intent.OnBackClicked)
                },
                onDropClickListener = {
                    accept(InventoryReservesStore.Intent.OnDropClicked)
                },
                onParamClickListener = {
                    accept(InventoryReservesStore.Intent.OnParamClicked(it))
                },
                onInventoryCreateClickListener = {
                    accept(InventoryReservesStore.Intent.OnCreateDocumentClicked)
                },
                onPageChanged = {
                    accept(InventoryReservesStore.Intent.OnSelectPage(it))
                },
                onParamCrossClickListener = {
                    accept(InventoryReservesStore.Intent.OnParamCrossClicked(it))
                },
                onReservesClickListener = {

                }
            )
        }
    }
}