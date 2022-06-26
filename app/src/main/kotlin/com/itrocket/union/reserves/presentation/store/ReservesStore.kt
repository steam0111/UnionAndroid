package com.itrocket.union.reserves.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailArguments
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.view.ReservesComposeFragmentDirections

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
        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = ReservesComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.RESERVES
                    )
                )
        }

        data class ShowDetail(val item: ReservesDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = ReservesComposeFragmentDirections.toReserveDetail(
                    ReserveDetailArguments(argument = item)
                )
        }
    }
}