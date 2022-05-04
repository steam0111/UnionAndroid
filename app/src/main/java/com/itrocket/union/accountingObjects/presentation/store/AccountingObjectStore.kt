package com.itrocket.union.accountingObjects.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

interface AccountingObjectStore :
    Store<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label> {

    sealed class Intent {
        data class OnItemClicked(val item: AccountingObjectDomain) :
            Intent()

        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        object OnSearchClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val accountingObjects: List<AccountingObjectDomain> = listOf()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowSearch : Label()
        object ShowFilter : Label()
        data class ShowDetail(val item: AccountingObjectDomain) : Label()
    }
}