package com.itrocket.union.bottomActionMenu.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_CODE
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_LABEL
import com.itrocket.union.documents.domain.entity.ObjectAction

interface BottomActionMenuStore :
    Store<BottomActionMenuStore.Intent,
            BottomActionMenuStore.State,
            BottomActionMenuStore.Label> {
    sealed class Intent {
        data class OnTypeClicked(
            val type: ObjectAction,
            val item: AccountingObjectDomain,
            val listAO: List<AccountingObjectDomain>
        ) :
            Intent()
    }

    data class State(
        val types: List<ObjectAction>,
        val item: AccountingObjectDomain,
        val listAO: List<AccountingObjectDomain>
    )

    sealed class Label {
        data class GoBack(override val result: BottomActionMenuResult?) :
            BottomActionMenuStore.Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = BOTTOM_ACTION_RESULT_CODE

            override val resultLabel: String
                get() = BOTTOM_ACTION_RESULT_LABEL
        }

        data class Error(override val message: String) : BottomActionMenuStore.Label(),
            DefaultNavigationErrorLabel

        data class ShowDetail(val item: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = item)
                )
        }

        data class DeleteCard(override val result: BottomActionMenuResult) : Label(),
            GoBackNavigationLabel {
            override val resultCode: String
                get() = BOTTOM_ACTION_RESULT_CODE

            override val resultLabel: String
                get() = BOTTOM_ACTION_RESULT_LABEL
        }
    }
}