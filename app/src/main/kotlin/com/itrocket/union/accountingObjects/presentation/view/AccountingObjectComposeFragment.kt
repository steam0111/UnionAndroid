package com.itrocket.union.accountingObjects.presentation.view

import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.accountingObjects.AccountingObjectModule.ACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore

class AccountingObjectComposeFragment :
    BaseComposeFragment<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label>(
        ACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER
    ) {

    @OptIn(ExperimentalPagerApi::class)
    override fun renderState(
        state: AccountingObjectStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AccountingObjectScreen(
                state = state,
                appInsets = appInsets,
                onSearchClickListener = {
                    accept(AccountingObjectStore.Intent.OnSearchClicked)
                },
                onBackClickListener = {
                    accept(AccountingObjectStore.Intent.OnBackClicked)
                },
                onFilterClickListener = {
                    accept(AccountingObjectStore.Intent.OnFilterClicked)
                },
                onAccountingObjectListener = {
                    accept(AccountingObjectStore.Intent.OnItemClicked(it))
                }
            )
        }
    }
}