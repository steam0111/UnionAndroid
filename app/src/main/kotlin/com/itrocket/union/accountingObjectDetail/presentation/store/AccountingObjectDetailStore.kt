package com.itrocket.union.accountingObjectDetail.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment

interface AccountingObjectDetailStore :
    Store<AccountingObjectDetailStore.Intent, AccountingObjectDetailStore.State, AccountingObjectDetailStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnCheckedFullCharacteristics(val isChecked: Boolean) : Intent()
        data class OnPageSelected(val selectedPage: Int) : Intent()
        object OnReadingModeClicked : Intent()
        object OnDocumentSearchClicked : Intent()
        object OnDocumentAddClicked : Intent()
    }

    data class State(
        val accountingObjectDomain: AccountingObjectDomain,
        val isLoading: Boolean = false,
        val isFullCharacteristicChecked: Boolean = false,
        val readingMode: ReadingModeTab = ReadingModeTab.RFID,
        val selectedPage: Int = 0
    )

    sealed class Label {
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        object GoBack : Label(), GoBackNavigationLabel
        data class ShowReadingMode(val readingMode: ReadingModeTab) : Label(),
            ShowBottomSheetNavigationLabel {

            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }
    }
}