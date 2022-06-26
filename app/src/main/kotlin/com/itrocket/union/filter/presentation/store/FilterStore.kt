package com.itrocket.union.filter.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.filter.presentation.view.FilterComposeFragmentDirections
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult

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
        val resultCount: Int = 0,
        val from: CatalogType = CatalogType.DEFAULT
    )

    sealed class Label {
        class GoBack(override val result: SelectParamsResult? = null) : Label(),
            GoBackNavigationLabel {
            override val resultCode: String = FilterComposeFragment.FILTER_RESULT_CODE
            override val resultLabel: String = FilterComposeFragment.FILTER_RESULT_LABEL
        }

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

        data class ShowLocation(val location: LocationParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = FilterComposeFragmentDirections.toLocation(LocationArguments(location))

        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}