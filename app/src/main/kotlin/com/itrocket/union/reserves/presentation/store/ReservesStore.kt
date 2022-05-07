package com.itrocket.union.reserves.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface ReservesStore : Store<ReservesStore.Intent, ReservesStore.State, ReservesStore.Label> {

    sealed class Intent {
        data class OnItemClicked(val item: ReservesDomain) :
            Intent()

        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        object OnSearchClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val reserves: List<ReservesDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowSearch : Label()
        object ShowFilter : Label()
        object ShowDetail : Label()
    }
}