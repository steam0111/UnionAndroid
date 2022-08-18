package com.itrocket.union.inventory.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryContainer.presentation.view.InventoryContainerComposeFragmentDirections
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments
import com.itrocket.union.structural.presentation.store.StructuralArguments
import com.itrocket.union.structural.presentation.store.StructuralResult

interface InventoryStore :
    Store<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnCreateDocumentClicked : Intent()
        data class OnSelectPage(val selectedPage: Int) : Intent()
        data class OnParamClicked(val param: ParamDomain) : Intent()
        data class OnParamCrossClicked(val param: ParamDomain) : Intent()
        data class OnParamsChanged(val params: List<ParamDomain>) : Intent()
        data class OnLocationChanged(val locationResult: LocationResult) : Intent()
        data class OnStructuralChanged(val structural: StructuralResult) : Intent()
    }

    data class State(
        val isAccountingObjectsLoading: Boolean = false,
        val isCreateInventoryLoading: Boolean = false,
        val selectedPage: Int = 0,
        val accountingObjectList: List<AccountingObjectDomain> = listOf(),
        val params: List<ParamDomain> = listOf(
            StructuralParamDomain(manualType = ManualType.STRUCTURAL),
            ParamDomain(id = "", value = "", type = ManualType.MOL),
            LocationParamDomain(manualType = ManualType.LOCATION_INVENTORY),
            ParamDomain(id = "", value = "", type = ManualType.INVENTORY_BASE)
        ),
        val isCanCreateInventory: Boolean = true
    )

    sealed class Label {
        object GoBack : Label()
        data class ShowCreateInventory(
            val inventoryCreate: InventoryCreateDomain
        ) : Label()

        data class ShowStructural(val structural: StructuralParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toStructural(
                    StructuralArguments(structural = structural, isCanEdit = true)
                )
        }

        data class ShowLocation(val location: LocationParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toLocation(
                    LocationArguments(location = location)
                )
        }

        data class ShowParamSteps(
            val currentStep: Int,
            val params: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        params = params,
                        currentStep = currentStep
                    )
                )
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}