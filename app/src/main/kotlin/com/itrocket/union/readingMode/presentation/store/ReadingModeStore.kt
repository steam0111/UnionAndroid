package com.itrocket.union.readingMode.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
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
        val isManualInputEnabled: Boolean = false,
        val tabs: List<ReadingModeTab>,
        val selectedTab: ReadingModeTab = ReadingModeTab.RFID
    )

    sealed class Label {
        data class ResultReadingTab(val readingMode: ReadingModeTab) : Label()
    }
}