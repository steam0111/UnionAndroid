package com.itrocket.union.documentCreate.presentation.store

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
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectArguments
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateComposeFragmentDirections
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nfcReader.domain.entity.NfcReaderType
import com.itrocket.union.nfcReader.presentation.store.NfcReaderArguments
import com.itrocket.union.nfcReader.presentation.store.NfcReaderResult
import com.itrocket.union.nfcReader.presentation.view.NfcReaderComposeFragment
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.store.ReservesArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountResult
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments

interface DocumentCreateStore :
    Store<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnSaveClicked : Intent()
        object OnSettingsClicked : Intent()
        object OnChooseAccountingObjectClicked : Intent()
        object OnChooseReserveClicked : Intent()
        object OnCompleteClicked : Intent()
        data class OnSelectPage(val selectedPage: Int) : Intent()
        data class OnParamClicked(val param: ParamDomain) : Intent()
        data class OnParamCrossClicked(val param: ParamDomain) : Intent()
        data class OnParamsChanged(val params: List<ParamDomain>) : Intent()
        data class OnReserveSelected(val reserve: ReservesDomain) :
            Intent()

        data class OnReserveCountSelected(val result: SelectCountResult) : Intent()
        data class OnAccountingObjectSelected(val accountingObjectDomain: AccountingObjectDomain) :
            Intent()

        data class OnNewAccountingObjectRfidHandled(val rfids: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnReserveClicked(val reserve: ReservesDomain) : Intent()
        data class OnNfcReaderClose(val nfcReaderResult: NfcReaderResult) : Intent()
        data class OnReadingModeTabChanged(val readingModeTab: ReadingModeTab) : Intent()
        data class OnManualInput(val readingModeResult: ReadingModeResult) : Intent()
        data class OnDeleteAccountingObjectClicked(val accountingObjectId: String) : Intent()
        data class OnDeleteReserveClicked(val reserveId: String) : Intent()
        object OnDismissConfirmDialog : Intent()
        object OnConfirmActionClick : Intent()
    }

    data class State(
        val document: DocumentDomain,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val reserves: List<ReservesDomain> = listOf(),
        val params: List<ParamDomain>,
        val isLoading: Boolean = false,
        val selectedPage: Int = 0,
        val departureLocation: List<LocationDomain> = emptyList(),
        val confirmDialogType: AlertType = AlertType.NONE,
        val canUpdate: Boolean = false,
        val canCreate: Boolean = false,
        val readingModeTab: ReadingModeTab,
        val canDelete: Boolean = false,
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowWarning(val message: Int) : Label()

        data class ShowAccountingObjects(
            val params: List<ParamDomain>,
            val selectedAccountingObjectIds: List<String>
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toAccountingObjects(
                    AccountingObjectArguments(
                        params = params,
                        selectedAccountingObjectIds = selectedAccountingObjectIds,
                        isFromDocument = true
                    )
                )
        }

        data class ShowReserves(
            val params: List<ParamDomain>,
            val selectedReserveIds: List<String>
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toReserves(
                    ReservesArguments(
                        params = params,
                        selectedReservesIds = selectedReserveIds,
                        isFromDocument = true
                    )
                )
        }

        data class ShowParamSteps(
            val currentFilter: ParamDomain,
            val allParams: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        currentFilter = currentFilter,
                        allParams = allParams
                    )
                )
        }

        object ShowReadingMode : Label(),
            ShowBottomSheetNavigationLabel {

            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        data class ShowSelectCount(val id: String, val count: Long) : Label(),
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

        data class ShowNfcReader(val document: DocumentDomain) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    NfcReaderComposeFragment.NFC_READER_ARGUMENT to NfcReaderArguments(
                        NfcReaderType.DocumentConduct(
                            document
                        )
                    )
                )
            override val containerId: Int = R.id.mainActivityNavHostFragment
            override val fragment: Fragment
                get() = NfcReaderComposeFragment()
        }
    }
}