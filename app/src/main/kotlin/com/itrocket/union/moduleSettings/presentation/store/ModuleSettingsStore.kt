package com.itrocket.union.moduleSettings.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel

interface ModuleSettingsStore :
    Store<ModuleSettingsStore.Intent, ModuleSettingsStore.State, ModuleSettingsStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDefineCursorClicked : Intent()
        object OnSaveClicked : Intent()
        object OnDropdownDismiss : Intent()
        object OnDropDownReadPowerDismiss : Intent()
        object OnDropDownWritePowerDismiss : Intent()
        object OnDropdownOpenClicked : Intent()
        object OnDropDownOpenReadPowerClicked:Intent()
        object OnDropDownOpenWritePowerClicked:Intent()
        data class OnDropdownItemClicked(val service: String) : Intent()
        data class OnServicesHandled(val services: List<String>) : Intent()
        data class OnDefaultServiceHandled(val service: String) : Intent()
        data class OnCursorDefined(val keyCode: Int) : Intent()
        data class OnPowerOfReaderHandled(val listPowerOfReader: List<Int>) : Intent()
        data class OnDefaultReadPowerHandled(val readPower: Int) : Intent()
        data class OnDefaultWritePowerHandled(val writePower: Int) : Intent()
        data class OnDropDownItemReadPowerClicked(val readPower: Int) : Intent()
        data class OnDropDownItemWritePowerClicked(val writePower: Int) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isDefineWait: Boolean = false,
        val defaultService: String = "",
        val services: List<String> = listOf(),
        val keyCode: Int = 0,
        val dropdownExpanded: Boolean = false,
        val dropDownReadPowerExpanded: Boolean = false,
        val dropDownWritePowerExpanded: Boolean = false,
        val defaultReadPower: String = "60",
        val defaultWritePower: String = "60",
        val listPowerOfReader: List<Int> = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}