package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL

interface SelectActionWithValuesBottomMenuStore :
    Store<SelectActionWithValuesBottomMenuStore.Intent, SelectActionWithValuesBottomMenuStore.State, SelectActionWithValuesBottomMenuStore.Label> {
    sealed class Intent {
        data class OnTypeClicked(
            val objectAction: ObjectAction,
            val accountingObjectDomain: AccountingObjectDomain,
            val accountingObjects: List<AccountingObjectDomain>
        ) : Intent()
    }

    data class State(
        val objectActions: List<ObjectAction>,
        val accountingObjectDomain: AccountingObjectDomain,
        val accountingObjects: List<AccountingObjectDomain>
    )

    sealed class Label {
        data class GoBack(override val result: SelectActionWithValuesBottomMenuResult?) :
            SelectActionWithValuesBottomMenuStore.Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE

            override val resultLabel: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL
        }

        data class Error(override val message: String) :
            SelectActionWithValuesBottomMenuStore.Label(),
            DefaultNavigationErrorLabel

        data class ShowDetail(val item: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = item)
                )
        }

        data class DeleteCard(override val result: SelectActionWithValuesBottomMenuResult) :
            Label(),
            GoBackNavigationLabel {
            override val resultCode: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE

            override val resultLabel: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL
        }
    }
}