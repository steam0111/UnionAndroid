package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ActionsWithIdentifyObjects
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment.Companion.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL

interface SelectActionWithValuesBottomMenuStore :
    Store<SelectActionWithValuesBottomMenuStore.Intent, SelectActionWithValuesBottomMenuStore.State, SelectActionWithValuesBottomMenuStore.Label> {
    sealed class Intent {
        data class OnTypeClicked(val actionsWithIdentifyObjects: ActionsWithIdentifyObjects) :
            Intent()
    }

    data class State(
        val actionsWithIdentifyObjects: List<ActionsWithIdentifyObjects> = ActionsWithIdentifyObjects.values()
            .toList(),
        val accountingObject: AccountingObjectDomain,
    )

    sealed class Label {
        data class GoBack(override val result: SelectActionWithValuesBottomMenuResult?) :
            Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_CODE

            override val resultLabel: String
                get() = SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_RESULT_LABEL
        }

        data class Error(override val message: String) :
            Label(),
            DefaultNavigationErrorLabel

    }
}