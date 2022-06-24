package com.itrocket.union.filter.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.filter.presentation.view.FilterComposeFragmentDirections
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments

interface FilterStore : Store<FilterStore.Intent, FilterStore.State, FilterStore.Label> {

    sealed class Intent {
        data class OnFieldClicked(val filter: ParamDomain) : Intent()
        data class OnFilterChanged(val filters: List<ParamDomain>) : Intent()
        data class OnFilterLocationChanged(val locationResult: LocationResult) : Intent()
        object OnShowClicked : Intent()
        object OnCrossClicked : Intent()
        object OnDropClicked : Intent()
    }

    data class State(
        val params: Params,
        val resultCount: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowFilters(
            val currentStep: Int,
            val filters: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = FilterComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        params = filters,
                        currentStep = currentStep
                    )
                )
        }

        data class ShowLocation(val location: LocationParamDomain) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = FilterComposeFragmentDirections.toLocation(LocationArguments(location))

        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}