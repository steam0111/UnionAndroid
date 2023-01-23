package com.itrocket.union.identify.presentation.store

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
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.identify.domain.NomenclatureReserveDomain
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentDirections
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailArguments
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuArguments
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store.SelectActionWithValuesBottomMenuResult
import com.itrocket.union.selectActionWithValuesBottomMenu.presentation.view.SelectActionWithValuesBottomMenuFragment
import com.itrocket.union.ui.listAction.DialogAction
import com.itrocket.union.ui.listAction.DialogActionType

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        object OnReadingModeClicked : Intent()
        object OnDropClicked : Intent()
        data class OnSelectPage(val selectedPage: Int) : Intent()
        object OnBackClicked : Intent()
        object OnPlusClicked : Intent()
        object OnListActionDialogDismissed : Intent()
        data class OnNomenclatureReserveClicked(val nomenclatureReserveDomain: NomenclatureReserveDomain) :
            Intent()

        data class OnListActionDialogClicked(val dialogActionType: DialogActionType) : Intent()
        data class OnManualInput(val readingModeResult: ReadingModeResult) : Intent()

        data class OnItemClicked(val accountingObject: AccountingObjectDomain) : Intent()
        data class OnAccountingObjectActionsResultHandled(val result: SelectActionWithValuesBottomMenuResult) :
            Intent()

        data class OnNewRfidHandled(val rfids: List<String>) :
            Intent()

        data class OnNewBarcodeHandled(val barcode: String) :
            Intent()

        data class OnAccountingObjectSelected(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        object OnAccountingObjectClosed : Intent()

        data class OnErrorHandled(val throwable: Throwable) : Intent()
    }

    data class State(
        val isIdentifyLoading: Boolean = false,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val nomenclatureReserves: List<NomenclatureReserveDomain> = listOf(),
        val selectedPage: Int = 0,
        val readingModeTab: ReadingModeTab = ReadingModeTab.RFID,
        val dialogType: AlertType = AlertType.NONE,
        val loadingDialogAction: DialogActionType? = null,
        val canUpdateAccountingObjects: Boolean = false,
        val listDialogAction: List<DialogAction> = listOf(
            DialogAction(
                type = DialogActionType.WRITE_OFF,
                actionTextId = R.string.common_write_off
            )
        ),
        val nomenclatureRfids: List<String> = listOf()
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

        data class ShowNomenclature(val nomenclatureId: String) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toNomenclatureDetail(
                    NomenclatureDetailArguments(nomenclatureId)
                )

        }

        data class ShowAccountingObjectDetail(val accountingObject: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = accountingObject)
                )
        }

        data class ShowActions(
            val accountingObject: AccountingObjectDomain,
        ) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    SelectActionWithValuesBottomMenuFragment.SELECT_ACTION_WITH_VALUES_BOTTOM_MENU_ARGS to SelectActionWithValuesBottomMenuArguments(
                        accountingObject = accountingObject,
                    ),
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = SelectActionWithValuesBottomMenuFragment()
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}
