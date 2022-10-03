package com.itrocket.union.manualInput.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.manualInput.presentation.view.ManualInputComposeFragment

interface ManualInputStore :
    Store<ManualInputStore.Intent, ManualInputStore.State, ManualInputStore.Label> {

    sealed class Intent {
        data class OnTextChanged(val text: String) : Intent()
        object OnAcceptClicked : Intent()
        object OnCloseClicked : Intent()
    }

    data class State(
        val text: String = "",
        val type: ManualInputType
    )

    sealed class Label {
        data class GoBack(override val result: ManualInputResult? = null) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = ManualInputComposeFragment.MANUAL_INPUT_CODE

            override val resultLabel: String
                get() = ManualInputComposeFragment.MANUAL_INPUT_RESULT
        }
    }
}