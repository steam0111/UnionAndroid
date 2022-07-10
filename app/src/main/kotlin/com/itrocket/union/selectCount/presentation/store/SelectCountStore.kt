package com.itrocket.union.selectCount.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment.Companion.SELECT_COUNT_RESULT_CODE
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment.Companion.SELECT_COUNT_RESULT_LABEL

interface SelectCountStore :
    Store<SelectCountStore.Intent, SelectCountStore.State, SelectCountStore.Label> {

    sealed class Intent {
        data class OnCountChanged(val count: String) : Intent()
        object OnAcceptClicked : Intent()
    }

    data class State(
        val id: String,
        val count: Long
    )

    sealed class Label {
        data class GoBack(override val result: SelectCountResult?) : Label(),
            GoBackNavigationLabel {
            override val resultCode: String
                get() = SELECT_COUNT_RESULT_CODE

            override val resultLabel: String
                get() = SELECT_COUNT_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}