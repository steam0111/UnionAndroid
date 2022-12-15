package com.itrocket.union.reserveDetail.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.labelType.presentation.store.LabelTypeArguments
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserveDetail.presentation.view.ReserveDetailComposeFragmentDirections
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorDomain

interface ReserveDetailStore :
    Store<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label> {

    sealed class Intent {
        object OnMarkingClicked : Intent()
        object OnBackClicked : Intent()
        object OnReadingModeClicked : Intent()
        object OnDocumentSearchClicked : Intent()
        object OnTriggerPressed : Intent()
        object OnTriggerReleased : Intent()
        data class OnWriteEpcHandled(val rfid: String) : Intent()
        data class OnWriteEpcError(val error: String) : Intent()
        data class OnErrorHandled(val error: Throwable) : Intent()
        object OnDocumentAddClicked : Intent()
        object OnDismissed : Intent()
        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        object OnLabelTypeEditClicked : Intent()
        data class OnLabelTypeSelected(val labelTypeId: String) : Intent()
    }

    data class State(
        val reserve: ReservesDomain? = null,
        val isLoading: Boolean = false,
        val readingMode: ReadingModeTab = ReadingModeTab.RFID,
        val canUpdate: Boolean = false,
        val showButtons: Boolean = false,
        val rfid: String? = null,
        val dialogType: AlertType = AlertType.NONE,
        val rfidError: String = "",
        val terminalRemainsNumerator: TerminalRemainsNumeratorDomain? = null
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowReadingMode(val readingMode: ReadingModeTab) : Label(),
            ShowBottomSheetNavigationLabel {

            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        object ShowLabelTypes : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = ReserveDetailComposeFragmentDirections.toLabelType(
                    LabelTypeArguments(isSelectMode = true)
                )
        }
    }
}