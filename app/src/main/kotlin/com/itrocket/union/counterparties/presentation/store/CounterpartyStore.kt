package com.itrocket.union.counterparties.presentation.store

import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.conterpartyDetail.presentation.store.CounterpartyDetailArguments
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.union.counterparties.presentation.view.CounterpartyComposeFragment
import com.itrocket.union.counterparties.presentation.view.CounterpartyComposeFragmentDirections

interface CounterpartyStore :
    Store<CounterpartyStore.Intent, CounterpartyStore.State, CounterpartyStore.Label> {

    sealed class Intent {
        object OnSearchClicked : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        data class OnCounterpartyClicked(val id: String) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val counterparties: List<CounterpartyDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = ""
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowDetail(val id: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = CounterpartyComposeFragmentDirections.toCounterpartyDetail(
                    CounterpartyDetailArguments(id = id)
                )
        }
    }
}