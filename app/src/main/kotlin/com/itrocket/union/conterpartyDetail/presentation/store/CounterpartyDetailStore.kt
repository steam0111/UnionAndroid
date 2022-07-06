package com.itrocket.union.conterpartyDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain

interface CounterpartyDetailStore :
    Store<CounterpartyDetailStore.Intent, CounterpartyDetailStore.State, CounterpartyDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val item: CounterpartyDetailDomain = CounterpartyDetailDomain(),
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}