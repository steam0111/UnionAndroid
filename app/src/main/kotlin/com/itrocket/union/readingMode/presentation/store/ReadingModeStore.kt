package com.itrocket.union.readingMode.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

interface ReadingModeStore :
    Store<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label> {

    sealed class Intent {
        data class OnReadingModeSelected(val readingMode: ReadingModeTab) : Intent()
        data class OnReaderPowerClicked(val readerPower: Int) : Intent()
        object OnCameraClicked : Intent()
        object OnSettingsClicked : Intent()
        object OnManualInputClicked : Intent()
    }

    data class State(
        val tabs: List<ReadingModeTab>,
        val selectedTab: ReadingModeTab = ReadingModeTab.RFID,
        val readerPower: Int = 0
    )

    sealed class Label {
        data class ResultReadingTab(val readingMode: ReadingModeTab) : Label()
    }
}