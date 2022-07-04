package com.itrocket.union.chooseAction.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.chooseAction.presentation.view.ChooseActionComposeFragment.Companion.CHOOSE_ACTION_RESULT_CODE
import com.itrocket.union.chooseAction.presentation.view.ChooseActionComposeFragment.Companion.CHOOSE_ACTION_RESULT_LABEL
import com.itrocket.union.documents.domain.entity.ObjectType

interface ChooseActionStore :
    Store<ChooseActionStore.Intent, ChooseActionStore.State, ChooseActionStore.Label> {

    sealed class Intent {
        data class OnTypeClicked(val type: ObjectType) : Intent()
    }

    data class State(
        val types: List<ObjectType>
    )

    sealed class Label {
        data class GoBack(override val result: ChooseActionResult?) : Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = CHOOSE_ACTION_RESULT_CODE

            override val resultLabel: String
                get() = CHOOSE_ACTION_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}