package com.itrocket.union.regions.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.regions.domain.entity.RegionDomain

interface RegionStore : Store<RegionStore.Intent, RegionStore.State, RegionStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnRegionClicked(val region: RegionDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val regions: List<RegionDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}