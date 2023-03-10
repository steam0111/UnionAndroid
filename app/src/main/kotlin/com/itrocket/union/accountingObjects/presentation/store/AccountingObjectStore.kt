package com.itrocket.union.accountingObjects.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragment
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.manual.ParamDomain

interface AccountingObjectStore :
    Store<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label> {

    sealed class Intent {
        data class OnItemClicked(val item: AccountingObjectDomain) :
            Intent()

        class OnFilterResult(val params: List<ParamDomain>) : Intent()
        object OnFilterClicked : Intent()
        object OnBackClicked : Intent()
        object OnSearchClicked : Intent()
        object OnLoadNext : Intent()
        object OnInfoClicked : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        object DismissDialog : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isInfoDialogVisible: Boolean = false,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val isShowSearch: Boolean = false,
        val searchText: String = "",
        val isListEndReached: Boolean = false,
        val params: List<ParamDomain>,
        val positionsCount: Long = 0,
        val allCount: Long = 0
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowWarning(val message: Int) : Label()
        data class GoBack(override val result: AccountingObjectResult? = null) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT_CODE

            override val resultLabel: String
                get() = AccountingObjectComposeFragment.ACCOUNTING_OBJECT_RESULT
        }

        data class ShowFilter(val filters: List<ParamDomain>) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.AccountingObjects
                    )
                )
        }

        data class ShowDetail(val item: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = item)
                )
        }
    }
}