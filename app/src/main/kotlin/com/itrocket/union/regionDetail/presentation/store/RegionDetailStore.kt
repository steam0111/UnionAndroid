package com.itrocket.union.regionDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain

interface RegionDetailStore :
    Store<RegionDetailStore.Intent, RegionDetailStore.State, RegionDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: RegionDetailDomain = RegionDetailDomain(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}