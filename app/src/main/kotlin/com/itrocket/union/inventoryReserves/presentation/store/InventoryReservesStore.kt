package com.itrocket.union.inventoryReserves.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface InventoryReservesStore :
    Store<InventoryReservesStore.Intent, InventoryReservesStore.State, InventoryReservesStore.Label> {

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
        val isLoading: Boolean = false,
        val selectedPage: Int = 0,
        val reserves: List<ReservesDomain> = listOf(),
        val params: List<ParamDomain> = listOf(
            ParamDomain(paramValue = null, type = ManualType.ORGANIZATION),
            ParamDomain(paramValue = null, type = ManualType.MOL),
            ParamDomain(paramValue = null, type = ManualType.LOCATION),
        )
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}