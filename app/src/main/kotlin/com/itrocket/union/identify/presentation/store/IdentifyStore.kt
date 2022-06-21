package com.itrocket.union.identify.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentDirections

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        data class OnItemClicked(val item: AccountingObjectDomain) :
            Intent()

        object OnFilterClicked : IdentifyStore.Intent()
        object OnBackClicked : IdentifyStore.Intent()
        object OnSearchClicked : IdentifyStore.Intent()
    }

    data class State(
//        val accountingObjectDomain: AccountingObjectDomain,

        val isLoading: Boolean = false,
        val identifies: List<AccountingObjectDomain> = listOf(),
        val selectedPage: Int = 0,
        val accountingObjects: List<AccountingObjectDomain> = listOf()


    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowSearch : IdentifyStore.Label()

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowFilter(val filters: List<FilterDomain>) : IdentifyStore.Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toFilter(FilterArguments(filters))
        }

        data class ShowDetail(val item: AccountingObjectDomain) :
            IdentifyStore.Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = item)
                )
        }
    }
}