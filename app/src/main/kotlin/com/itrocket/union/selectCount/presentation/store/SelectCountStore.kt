package com.itrocket.union.selectCount.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment.Companion.SELECT_COUNT_RESULT_CODE
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment.Companion.SELECT_COUNT_RESULT_LABEL
import java.math.BigDecimal

interface SelectCountStore :
    Store<SelectCountStore.Intent, SelectCountStore.State, SelectCountStore.Label> {

    sealed class Intent {
        data class OnCountChanged(val count: String) : Intent()
        object OnAcceptClicked : Intent()
    }

    data class State(
        val id: String,
        val count: BigDecimal
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