package com.itrocket.union.dataCollect.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment

interface DataCollectStore :
    Store<DataCollectStore.Intent, DataCollectStore.State, DataCollectStore.Label> {
    sealed class Intent {
        object OnReadingModeClicked : Intent()
        object OnDropClicked : Intent()
        object OnBackClicked : Intent()
        data class OnNewAccountingObjectRfidHandled(val rfids: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()
    }

    data class State(
        val scanningObjects: List<String> = listOf(),
        val selectedPage: Int = 0,
        val count: Int = 0
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        object ShowReadingMode : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf()
            override val containerId: Int = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }
    }
}