package com.itrocket.union.inventory.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_CODE
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_LABEL
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
        object OnInWorkClicked : Intent()
        object OnSaveClicked : Intent()
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
            StructuralParamDomain(manualType = ManualType.BALANCE_UNIT, clickable = false),
            ParamDomain(id = "", value = "", type = ManualType.MOL_IN_STRUCTURAL),
            LocationParamDomain(manualType = ManualType.LOCATION_INVENTORY),
            ParamDomain(id = "", value = "", type = ManualType.INVENTORY_BASE),
            ParamDomain(
                id = "",
                value = "",
                type = ManualType.INVENTORY_CHECKER,
                isClickable = false,
                isFilter = false
            )
        ),
        val canCreateInventory: Boolean = false,
        val canUpdateInventory: Boolean = false,
        val inventoryCreateDomain: InventoryCreateDomain?,
        val isDynamicSaveInventory: Boolean = false,
    )

    sealed class Label {
        class GoBack(override val result: InventoryResult) : Label(), GoBackNavigationLabel {
            override val resultCode: String = INVENTORY_RESULT_CODE
            override val resultLabel: String = INVENTORY_RESULT_LABEL
        }

        data class ShowCreateInventory(
            val inventoryCreate: InventoryCreateDomain
        ) : Label()

        data class ShowStructural(val structural: StructuralParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toStructural(
                    StructuralArguments(structural = structural, canEdit = true)
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
            val params: List<ParamDomain>,
            val allParams: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        params = params,
                        currentStep = currentStep,
                        allParams = allParams
                    )
                )
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}