package com.itrocket.union.changeScanData.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.readerPower.presentation.view.ReaderPowerComposeFragment

interface ChangeScanDataStore :
    Store<ChangeScanDataStore.Intent, ChangeScanDataStore.State, ChangeScanDataStore.Label> {

    sealed class Intent {
        data class OnPowerChanged(val power: Int?) : Intent()
        data class OnScanning(val scanningValue: String) : Intent()
        data class OnScanDataChanged(val scanData: String) : Intent()
        object OnPowerClicked : Intent()
        object OnBackClicked : Intent()
        object OnCancelClicked : Intent()
        object OnApplyClicked : Intent()
        data class OnErrorHandled(val throwable: Throwable) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val changeScanType: ChangeScanType,
        val currentScanValue: String? = null,
        val newScanValue: String = "",
        val isApplyEnabled: Boolean = false,
        val isScanDataExistError: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

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