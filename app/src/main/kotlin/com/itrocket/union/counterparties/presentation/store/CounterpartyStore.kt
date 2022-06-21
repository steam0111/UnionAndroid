package com.itrocket.union.counterparties.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain

interface CounterpartyStore :
    Store<CounterpartyStore.Intent, CounterpartyStore.State, CounterpartyStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnCounterpartyClicked(val item: CounterpartyDomain) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val counterparties: List<CounterpartyDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}