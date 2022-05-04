package com.itrocket.union.accountingObjectDetail.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeFragment
import com.itrocket.union.accountingObjectDetail.AccountingObjectDetailModule.ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore

class AccountingObjectDetailComposeFragment :
    BaseComposeFragment<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label>(
        ACCOUNTINGOBJECTDETAIL_VIEW_MODEL_QUALIFIER
    ) {
    override val navArgs by navArgs<AccountingObjectDetailComposeFragmentArgs>()

    override fun renderState(
        state: AccountingObjectDetailStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            AccountingObjectDetailScreen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnBackClicked)
                },
                onCheckedFullCharacteristicsChangeListener = {
                    accept(AccountingObjectDetailStore.Intent.OnCheckedFullCharacteristics(it))
                },
                onReadingModeClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnReadingModeClicked)
                },
                onDocumentSearchClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnDocumentSearchClicked)
                },
                onDocumentAddClickListener = {
                    accept(AccountingObjectDetailStore.Intent.OnDocumentAddClicked)
                },
                onPageChangeListener = {
                    accept(AccountingObjectDetailStore.Intent.OnPageSelected(it))
                }
            )
        }
    }
}