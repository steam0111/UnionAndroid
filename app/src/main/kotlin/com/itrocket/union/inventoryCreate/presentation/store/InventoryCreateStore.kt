package com.itrocket.union.inventoryCreate.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventory.presentation.store.InventoryResult
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_CODE
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_LABEL
import com.itrocket.union.inventoryCreate.domain.entity.AccountingObjectCounter
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.union.switcher.presentation.store.SwitcherArguments
import com.itrocket.union.switcher.presentation.store.SwitcherResult
import com.itrocket.union.switcher.presentation.view.SwitcherComposeFragment

interface InventoryCreateStore :
    Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> {

    sealed class Intent {
        data class OnAccountingObjectClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnAccountingObjectStatusChanged(val switcherResult: SwitcherResult) :
            Intent()

        data class OnNewAccountingObjectRfidHandled(val handledAccountingObjectIds: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnHideFoundAccountingObjectClicked : Intent()
        object OnAddNewClicked : Intent()
        object OnSaveClicked : Intent()
        object OnReadingClicked : Intent()
        object OnCompleteClicked : Intent()
        object OnSaveDismissed : Intent()
        object OnSaveConfirmed : Intent()
        object OnCompleteDismissed : Intent()
        object OnCompleteConfirmed : Intent()
        object OnSearchClicked : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        data class OnManualInput(val readingModeResult: ReadingModeResult) : Intent()
    }

    data class State(
        val inventoryDocument: InventoryCreateDomain,
        val isHideFoundAccountingObjects: Boolean = false,
        val newAccountingObjects: Set<AccountingObjectDomain> = setOf(),
        val isAddNew: Boolean = false,
        val isLoading: Boolean = false,
        val dialogType: AlertType = AlertType.NONE,
        val readingModeTab: ReadingModeTab,
        val canUpdate: Boolean = false,
        val searchText: String = "",
        val isShowSearch: Boolean = false,
        val isDynamicSaveInventory: Boolean = false,
        val searchAccountingObjects: List<AccountingObjectDomain> = listOf(),
        val accountingObjectCounter: AccountingObjectCounter = AccountingObjectCounter()
    )

    sealed class Label {
        data class GoBack(override val result: InventoryResult) : Label(), GoBackNavigationLabel {
            override val resultCode: String = INVENTORY_RESULT_CODE
            override val resultLabel: String = INVENTORY_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowChangeStatus(val switcherData: SwitcherDomain) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    SwitcherComposeFragment.SWITCHER_ARG to SwitcherArguments(
                        switcherData
                    )
                )
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = SwitcherComposeFragment()

        }

        data class ShowNewAccountingObjectDetail(val newAccountingObjectArgument: NewAccountingObjectArguments) :
            Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    NewAccountingObjectComposeFragment.NEW_ACCOUNTING_OBJECT_ARGUMENT to newAccountingObjectArgument
                )
            override val containerId: Int
                get() = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = NewAccountingObjectComposeFragment()

        }

        object ShowLeaveWithoutSave : Label()
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