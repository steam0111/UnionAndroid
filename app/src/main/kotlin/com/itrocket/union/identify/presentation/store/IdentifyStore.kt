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
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuArguments
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        object OnReadingModeClicked : Intent()
        object OnDropClicked : Intent()
        data class OnSelectPage(val selectedPage: Int) : Intent()
        object OnBackClicked : Intent()

        data class OnItemClicked(val accountingObject: AccountingObjectDomain) : Intent()
        data class OnDeleteFromSelectActionWithValuesBottomMenu(val accountingObjects: List<AccountingObjectDomain>) :
            Intent()

        data class OnNewAccountingObjectRfidHandled(val rfid: String) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnAccountingObjectSelected(val accountingObject: AccountingObjectDomain) :
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
            val accountingObject: AccountingObjectDomain,
            val accountingObjects: List<AccountingObjectDomain>
        ) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    SelectActionWithValuesBottomMenuFragment.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_ARGS to SelectActionWithValuesBottomMenuArguments(
                        accountingObject = accountingObject,
                        accountingObjects = accountingObjects
                    ),
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = SelectActionWithValuesBottomMenuFragment()
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
