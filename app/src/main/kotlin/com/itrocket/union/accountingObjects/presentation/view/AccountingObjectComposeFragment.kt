package com.itrocket.union.accountingObjects.presentation.view

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.FragmentResult
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.AccountingObjectModule.ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.filter.presentation.view.FilterComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsResult
import com.itrocket.union.utils.fragment.displayError

class AccountingObjectComposeFragment :
    BaseComposeFragment<AccountingObjectStore.Intent, AccountingObjectStore.State, AccountingObjectStore.Label>(
        ACCOUNTING_OBJECT_VIEW_MODEL_QUALIFIER
    ) {

    override val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                accept(AccountingObjectStore.Intent.OnBackClicked)
            }
        }
    }

    override val navArgs by navArgs<AccountingObjectComposeFragmentArgs>()

    override val fragmentResultList: List<FragmentResult>
        get() = listOf(
            FragmentResult(
                resultCode = FilterComposeFragment.FILTER_RESULT_CODE,
                resultLabel = FilterComposeFragment.FILTER_RESULT_LABEL,
                resultAction = {
                    (it as SelectParamsResult?)?.params?.let {
                        accept(AccountingObjectStore.Intent.OnFilterResult(it))
                    }
                }
            )
        )

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
                },
                onSearchTextChanged = {
                    accept(AccountingObjectStore.Intent.OnSearchTextChanged(it))
                },
                onLoadNext = {
                    accept(AccountingObjectStore.Intent.OnLoadNext)
                },
                onInfoClicked = {
                    accept(AccountingObjectStore.Intent.OnInfoClicked)
                },
                onDialogDismiss = {
                    accept(AccountingObjectStore.Intent.DismissDialog)
                }
            )
        }
    }

    override fun handleLabel(label: AccountingObjectStore.Label) {
        if (label is AccountingObjectStore.Label.ShowWarning) {
            displayError(label.message)
        } else {
            super.handleLabel(label)
        }
    }

    companion object {
        const val ACCOUNTING_OBJECT_RESULT = "accounting object result"
        const val ACCOUNTING_OBJECT_RESULT_CODE = "accounting object result code"
    }
}