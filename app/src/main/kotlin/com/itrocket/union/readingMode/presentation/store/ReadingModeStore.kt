package com.itrocket.union.readingMode.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.readerPower.presentation.view.ReaderPowerComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

interface ReadingModeStore :
    Store<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label> {

    sealed class Intent {
        data class OnReadingModeSelected(val readingMode: ReadingModeTab) : Intent()
        object OnCameraClicked : Intent()
        object OnSettingsClicked : Intent()
        object OnManualInputClicked : Intent()
    }

    data class State(
        val tabs: List<ReadingModeTab>,
        val selectedTab: ReadingModeTab = ReadingModeTab.RFID
    )

    sealed class Label {
        data class ResultReadingTab(val readingMode: ReadingModeTab) : Label()

        object ReaderPower : Label(), ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf()
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = ReaderPowerComposeFragment()

        }
    }
}