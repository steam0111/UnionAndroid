package com.itrocket.union.identify.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuArguments
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        object OnReadingModeClicked : Intent()
        object OnDropClicked : IdentifyStore.Intent()
        data class OnSelectPage(val selectedPage: Int) : IdentifyStore.Intent()
        object OnBackClicked : IdentifyStore.Intent()

        data class OnItemClicked(val item: AccountingObjectDomain) : Intent()
        data class OnDeleteFromBottomAction(val bottomActionResult: List<AccountingObjectDomain>) :
            Intent()

        data class OnNewAccountingObjectRfidsHandled(val rfids: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnAccountingObjectSelected(val accountingObjectDomain: AccountingObjectDomain) :
            Intent()
    }

    data class State(
        val isIdentifyLoading: Boolean = false,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val reserves: List<ReservesDomain> = listOf(),
        val selectedPage: Int = 0,
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

        data class ShowDetail(
            val item: AccountingObjectDomain,
            val listAO: List<AccountingObjectDomain>
        ) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to BottomActionMenuArguments(
                        accountingObjectDomain = item,
                        listAO = listAO
                    ),
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = BottomActionMenuFragment()
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
