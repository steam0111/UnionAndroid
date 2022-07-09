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
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
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

        data class OnNewAccountingObjectRfidsHandled(val handledAccountingObjectIds: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnHideFoundAccountingObjectClicked : Intent()
        object OnAddNewClicked : Intent()
        object OnSaveClicked : Intent()
        object OnReadingClicked : Intent()
        object OnInWorkClicked : Intent()
        object OnCompleteClicked : Intent()
    }

    data class State(
        val inventoryDocument: InventoryCreateDomain,
        val isHideFoundAccountingObjects: Boolean = false,
        val newAccountingObjects: Set<AccountingObjectDomain> = setOf(),
        val isAddNew: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label()
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