package com.itrocket.union.moduleSettings.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

interface ModuleSettingsStore :
    Store<ModuleSettingsStore.Intent, ModuleSettingsStore.State, ModuleSettingsStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDefineCursorClicked : Intent()
        object OnSaveClicked : Intent()
        object OnDropdownDismiss : Intent()
        object OnDropdownOpenClicked : Intent()
        data class OnDropdownItemClicked(val service: String) : Intent()
        data class OnServicesHandled(val services: List<String>) : Intent()
        data class OnDefaultServiceHandled(val service: String) : Intent()
        data class OnCursorDefined(val keyCode: Int) : Intent()
        object OnArrowUpClicked : Intent()
        object OnArrowDownClicked : Intent()
        data class OnDynamicSaveInventoryClicked(val isDynamicSaveInventory: Boolean) : Intent()
        data class OnPowerChanged(val newPowerText: String) : Intent()
        data class OnReadingModeTabClicked(val readingModeTab: ReadingModeTab) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isDefineWait: Boolean = false,
        val defaultService: String = "",
        val services: List<String> = listOf(),
        val keyCode: Int = 0,
        val dropdownExpanded: Boolean = false,
        val readerPower: Int? = null,
        val isDynamicSaveInventory: Boolean = false,
        val selectedReadingMode: ReadingModeTab = ReadingModeTab.RFID,
        val readingModeTabs: List<ReadingModeTab> = ReadingModeTab.values().toList()
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}