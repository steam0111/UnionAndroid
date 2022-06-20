package com.itrocket.union.location.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.location.presentation.view.LocationComposeFragment

interface LocationStore : Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> {

    sealed class Intent {
        data class OnPlaceSelected(val place: LocationDomain) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object OnBackClicked : Intent()
        object OnCrossClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnFinishClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val selectPlaceScheme: List<LocationDomain> = listOf(),
        val searchText: String = "",
        val levelHint: String = "",
        val placeValues: List<LocationDomain> = listOf()
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class GoBack(override val result: LocationResult? = null) : Label(), GoBackNavigationLabel {
            override val resultCode: String
                get() = LocationComposeFragment.LOCATION_RESULT_CODE

            override val resultLabel: String
                get() = LocationComposeFragment.LOCATION_RESULT
        }
    }
}