package com.itrocket.union.inventoryCreate.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.core.navigation.ToastNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.comment.presentation.store.CommentArguments
import com.itrocket.union.comment.presentation.store.CommentResult
import com.itrocket.union.comment.presentation.view.CommentComposeFragment
import com.itrocket.union.comment.presentation.view.CommentComposeFragment.Companion.COMMENT_ARGS
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventory.presentation.store.InventoryResult
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_CODE
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_LABEL
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseArguments
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseResult
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseComposeFragment
import com.itrocket.union.inventoryChoose.presentation.view.InventoryChooseComposeFragment.Companion.INVENTORY_CHOOSE_ARGUMENT
import com.itrocket.union.inventoryContainer.presentation.view.InventoryContainerComposeFragmentDirections
import com.itrocket.union.inventoryCreate.domain.InventoryPage
import com.itrocket.union.inventoryCreate.domain.entity.AccountingObjectCounter
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.selectCount.presentation.store.SelectCountArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountResult
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment
import java.math.BigDecimal

interface InventoryCreateStore :
    Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> {

    sealed class Intent {
        data class OnAccountingObjectClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnInventoryNomenclatureClicked(val inventoryNomenclature: InventoryNomenclatureDomain) :
            Intent()

        data class OnNewRfidHandled(val handledRfids: List<String>) :
            Intent()

        data class OnNewBarcodeHandled(val barcode: String) :
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
        object OnDeleteConfirmClicked : Intent()
        object OnDeleteDismissClicked : Intent()
        data class OnCommentResultHandled(val result: CommentResult) : Intent()
        data class OnInventoryChooseResultHandled(val result: InventoryChooseResult) : Intent()
        data class OnAccountingObjectLongClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnPageChanged(val page: Int) : Intent()

        data class OnStatusClicked(val accountingObject: AccountingObjectDomain) : Intent()
        data class OnSearchTextChanged(val searchText: String) : Intent()
        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        data class OnManualInput(val readingModeResult: ReadingModeResult) : Intent()
        data class OnAccountingObjectChanged(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnInventoryNomenclatureCountChanged(val result: SelectCountResult) : Intent()

        data class OnErrorHandled(val throwable: Throwable) : Intent()

        object OnExitConfirmed : Intent()
        object OnAlertDismissed : Intent()
        object OnDropConfirmed : Intent()
    }

    data class State(
        val inventoryDocument: InventoryCreateDomain,
        val isHideFoundAccountingObjects: Boolean = false,
        val isAddNew: Boolean = false,
        val isLoading: Boolean = false,
        val dialogType: AlertType = AlertType.NONE,
        val readingModeTab: ReadingModeTab = ReadingModeTab.RFID,
        val canUpdate: Boolean = false,
        val canComplete: Boolean = false,
        val searchText: String = "",
        val isShowSearch: Boolean = false,
        val isDynamicSaveInventory: Boolean = false,
        val isCompleteLoading: Boolean = false,
        val dialogRemovedItemId: String = "",
        val searchAccountingObjects: List<AccountingObjectDomain> = listOf(),
        val searchInventoryNomenclatures: List<InventoryNomenclatureDomain> = listOf(),
        val inventoryObjectCounter: AccountingObjectCounter = AccountingObjectCounter(),
        val isExistNonMarkingAccountingObject: Boolean = false,
        val selectedPage: InventoryPage = InventoryPage.ACCOUNTING_OBJECT,
        val readerPower: Int? = null
    )

    sealed class Label {
        data class GoBack(override val result: InventoryResult) : Label(), GoBackNavigationLabel {
            override val resultCode: String = INVENTORY_RESULT_CODE
            override val resultLabel: String = INVENTORY_RESULT_LABEL
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

        data class ShowAccountingObjectDetail(val accountingObjectDomain: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(accountingObjectDomain)
                )

        }

        data class ShowInventoryChoose(val accountingObject: AccountingObjectDomain) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    INVENTORY_CHOOSE_ARGUMENT to InventoryChooseArguments(
                        accountingObject
                    )
                )
            override val containerId: Int = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = InventoryChooseComposeFragment()

        }

        data class ShowComment(val accountingObject: AccountingObjectDomain) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    COMMENT_ARGS to CommentArguments(
                        entityId = accountingObject.id,
                        comment = accountingObject.comment.orEmpty()
                    )
                )
            override val containerId: Int = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = CommentComposeFragment()

        }

        data class ShowToast(override val message: String, override val backgroundColor: Int) :
            ToastNavigationLabel, Label()

        object ShowReadingMode : Label(),
            ShowBottomSheetNavigationLabel {

            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        data class ShowSelectCount(val id: String, val count: BigDecimal) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    SelectCountComposeFragment.SELECT_COUNT_ARG to SelectCountArguments(
                        id = id,
                        count = count
                    )
                )
            override val containerId: Int = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = SelectCountComposeFragment()

        }
    }
}