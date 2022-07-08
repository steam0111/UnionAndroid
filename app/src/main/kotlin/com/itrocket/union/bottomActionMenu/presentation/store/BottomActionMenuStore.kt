package com.itrocket.union.bottomActionMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
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
        data class OnTypeClicked(val type: ObjectAction) :Intent()
        object OnCreateDocClicked : Intent()
        class OnOpenItemClicked(val item: ReservesDomain) : Intent()
        class OnDeleteItemClicked(val item: ReservesDomain) : Intent()
    }

    data class State(
        val types: List<ObjectAction>,
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

    }
}