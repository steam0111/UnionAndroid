package com.itrocket.union.bottomActionMenu.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_CODE
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment.Companion.BOTTOM_ACTION_RESULT_LABEL
import com.itrocket.union.chooseAction.presentation.store.ChooseActionResult
import com.itrocket.union.chooseAction.presentation.store.ChooseActionStore
import com.itrocket.union.chooseAction.presentation.view.ChooseActionComposeFragment
import com.itrocket.union.chooseAction.presentation.view.ChooseActionComposeFragment.Companion.CHOOSE_ACTION_RESULT_CODE
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface BottomActionMenuStore :
    Store<BottomActionMenuStore.Intent,
            BottomActionMenuStore.State,
            BottomActionMenuStore.Label> {
    sealed class Intent {
        data class OnTypeClicked(val type: ObjectAction, val item: AccountingObjectDomain) :Intent()
        object OnCreateDocClicked : Intent()
        class OnOpenItemClicked(val item: ReservesDomain) : Intent()
        class OnDeleteItemClicked(val item: ReservesDomain) : Intent()
    }

    data class State(
        val types: List<ObjectAction>,
        val item: AccountingObjectDomain
//        val bottomActionMenuDocument: ReservesDomain
    )

    sealed class Label {
        data class GoBack(override val result: BottomActionMenuResult?) : BottomActionMenuStore.Label(),
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
    }
}