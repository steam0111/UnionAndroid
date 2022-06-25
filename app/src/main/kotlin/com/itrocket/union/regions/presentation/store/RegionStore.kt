package com.itrocket.union.regions.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.regionDetail.presentation.store.RegionDetailArguments
import com.itrocket.union.regions.domain.entity.RegionDomain
import com.itrocket.union.regions.presentation.view.RegionComposeFragmentDirections

interface RegionStore : Store<RegionStore.Intent, RegionStore.State, RegionStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnRegionClicked(val id: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val regions: List<RegionDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = RegionComposeFragmentDirections.toRegionDetail(
                    RegionDetailArguments(id = id)
                )
        }
    }
}