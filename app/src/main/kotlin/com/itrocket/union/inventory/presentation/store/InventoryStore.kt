package com.itrocket.union.inventory.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragmentDirections
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments

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
    }

    data class State(
        val isAccountingObjectsLoading: Boolean = false,
        val isCreateInventoryLoading: Boolean = false,
        val isCreateEnabled: Boolean = false,
        val selectedPage: Int = 0,
        val accountingObjectList: List<AccountingObjectDomain> = listOf(),
        val params: List<ParamDomain> = listOf(
            ParamDomain(id = "", value = "", type = ManualType.ORGANIZATION),
            ParamDomain(id = "", value = "", type = ManualType.MOL),
            LocationParamDomain(ids = listOf(), values = listOf()),
        )
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowCreateInventory(
            val inventoryCreate: InventoryCreateDomain
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryComposeFragmentDirections.toInventoryCreate(
                    InventoryCreateArguments(inventoryDocument = inventoryCreate)
                )

        }

        data class ShowLocation(val location: LocationParamDomain) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryComposeFragmentDirections.toLocation(
                    LocationArguments(location = location)
                )
        }

        data class ShowParamSteps(
            val currentStep: Int,
            val params: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        params = params,
                        currentStep = currentStep
                    )
                )
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}