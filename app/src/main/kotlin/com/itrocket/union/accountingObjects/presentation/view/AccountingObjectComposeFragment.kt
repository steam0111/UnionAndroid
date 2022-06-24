package com.itrocket.union.accountingObjects.presentation.view

import android.util.Log
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.accountingObjects.AccountingObjectModule.ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore

class AccountingObjectComposeFragment :
    BaseComposeFragment<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label>(
        ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER
    ) {

    override val navArgs by navArgs<AccountingObjectComposeFragmentArgs>()

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

    companion object {
        const val ACCOUNTING_OBJECT_RESULT = "accounting object result"
        const val ACCOUNTING_OBJECT_RESULT_CODE = "accounting object result code"
    }
}