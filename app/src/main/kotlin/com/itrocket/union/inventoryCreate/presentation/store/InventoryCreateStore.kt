package com.itrocket.union.inventoryCreate.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.NavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.entity.InventoryDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment

interface InventoryCreateStore :
    Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> {

    sealed class Intent {
        data class OnAccountingObjectClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnNewAccountingObjectsHandled(val handledAccountingObjectIds: List<String>) :
            Intent()

        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnHideFoundAccountingObjectClicked : Intent()
        object OnAddNewClicked : Intent()
        object OnSaveClicked : Intent()
        object OnReadingClicked : Intent()
    }

    data class State(
        val inventoryDocument: InventoryCreateDomain,
        val accountingObjects: List<AccountingObjectDomain>,
        val isHideFoundAccountingObjects: Boolean = false,
        val newAccountingObjects: Set<AccountingObjectDomain> = setOf(),
        val isAddNew: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        object ShowChangeStatus : Label()
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