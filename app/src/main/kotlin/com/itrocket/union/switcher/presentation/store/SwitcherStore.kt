package com.itrocket.union.switcher.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.union.switcher.presentation.view.SwitcherComposeFragment

interface SwitcherStore : Store<SwitcherStore.Intent, SwitcherStore.State, SwitcherStore.Label> {

    sealed class Intent {
        data class OnTabClicked(val page: Int) : Intent()
        object OnCrossClicked : Intent()
        object OnCancelClicked : Intent()
        object OnContinueClicked : Intent()
    }

    data class State(
        val switcherData: SwitcherDomain,
        val selectedPage: Int,
    )

    sealed class Label {
        data class GoBack(override val result: SwitcherResult?) : Label(), GoBackDialogNavigationLabel {
            override val resultCode: String
                get() = SwitcherComposeFragment.SWITCHER_RESULT_CODE

            override val resultLabel: String
                get() = SwitcherComposeFragment.SWITCHER_RESULT
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}