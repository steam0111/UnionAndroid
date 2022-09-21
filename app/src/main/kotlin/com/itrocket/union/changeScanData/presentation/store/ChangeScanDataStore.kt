package com.itrocket.union.changeScanData.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType

interface ChangeScanDataStore :
    Store<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label> {

    sealed class Intent {
        data class OnScanning(val scanningValue: String) : Intent()
        data class OnScanDataChanged(val scanData: String) : Intent()
        data class OnPowerClicked(val power: Int) : Intent()
        object OnBackClicked : Intent()
        object OnCancelClicked : Intent()
        object OnApplyClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val changeScanType: ChangeScanType,
        val currentScanValue: String? = null,
        val newScanValue: String = "",
        val isApplyEnabled: Boolean = false,
        val isScanDataExistError: Boolean = false,
        val readerPower: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}