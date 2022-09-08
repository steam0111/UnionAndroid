package com.itrocket.union.readerPower.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.union.readerPower.presentation.view.ReaderPowerComposeFragment

interface ReaderPowerStore :
    Store<ReaderPowerStore.Intent, ReaderPowerStore.State, ReaderPowerStore.Label> {

    sealed class Intent {
        object OnCancelClicked : Intent()
        object OnCrossClicked : Intent()
        object OnAcceptClicked : Intent()
        object OnArrowUpClicked : Intent()
        object OnArrowDownClicked : Intent()
        data class OnPowerChanged(val newPowerText: String?) : Intent()
    }

    data class State(
        val readerPower: Int? = null
    )

    sealed class Label {
        data class GoBack(override val result: ReaderPowerResult? = null) : Label(),
            GoBackDialogNavigationLabel {
            override val resultCode: String = ReaderPowerComposeFragment.READER_POWER_RESULT_CODE
            override val resultLabel: String = ReaderPowerComposeFragment.READER_POWER_RESULT
        }
    }
}