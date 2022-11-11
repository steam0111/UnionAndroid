package com.itrocket.union.readingMode.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackDialogNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.manualInput.presentation.store.ManualInputArguments
import com.itrocket.union.manualInput.presentation.store.ManualInputType
import com.itrocket.union.manualInput.presentation.view.ManualInputComposeFragment
import com.itrocket.union.readerPower.presentation.view.ReaderPowerComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment.Companion.READING_MODE_MANUAL_RESULT_CODE
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment.Companion.READING_MODE_MANUAL_RESULT_LABEL
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

interface ReadingModeStore :
    Store<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label> {

    sealed class Intent {
        data class OnReadingModeSelected(val readingMode: ReadingModeTab) : Intent()
        object OnRestartClicked : Intent()
        object OnSettingsClicked : Intent()
        object OnManualInputClicked : Intent()
        data class OnManualInput(val text: String) : Intent()
    }

    data class State(
        val tabs: List<ReadingModeTab>,
        val selectedTab: ReadingModeTab = ReadingModeTab.RFID
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ResultReadingTab(val readingMode: ReadingModeTab) : Label()

        data class ManualInput(val manualInputType: ManualInputType) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    ManualInputComposeFragment.MANUAL_INPUT_ARGS to ManualInputArguments(
                        manualType = manualInputType
                    )
                )
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = ManualInputComposeFragment()

        }

        data class GoBack(override val result: ReadingModeResult?) : Label(),
            GoBackDialogNavigationLabel {

            override val resultCode: String
                get() = READING_MODE_MANUAL_RESULT_CODE
            override val resultLabel: String
                get() = READING_MODE_MANUAL_RESULT_LABEL
        }

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