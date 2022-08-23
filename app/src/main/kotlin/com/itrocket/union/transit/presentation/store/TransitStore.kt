package com.itrocket.union.transit.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectArguments
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.documentCreate.presentation.view.DocumentConfirmAlertType
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateComposeFragmentDirections
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.store.ReservesArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountArguments
import com.itrocket.union.selectCount.presentation.store.SelectCountResult
import com.itrocket.union.selectCount.presentation.view.SelectCountComposeFragment
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments
import com.itrocket.union.structural.presentation.store.StructuralArguments
import com.itrocket.union.structural.presentation.store.StructuralResult
import com.itrocket.union.transit.presentation.view.TransitComposeFragmentDirections

interface TransitStore : Store<TransitStore.Intent, TransitStore.State, TransitStore.Label> {

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

        data class OnNewAccountingObjectRfidHandled(val rfid: String) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnLocationChanged(val location: LocationResult) : Intent()
        data class OnReserveClicked(val reserve: ReservesDomain) : Intent()
        data class OnStructuralChanged(val structural: StructuralResult) : Intent()
        object OnDismissConfirmDialog : Intent()
        object OnConfirmActionClick : Intent()
    }

    data class State(
        val transit: DocumentDomain,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val reserves: List<ReservesDomain> = listOf(),
        val params: List<ParamDomain>,
        val isLoading: Boolean = false,
        val selectedPage: Int = 0,
        val confirmDialogType: DocumentConfirmAlertType = DocumentConfirmAlertType.NONE
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

        data class ShowLocation(val location: LocationParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = TransitComposeFragmentDirections.toLocation(
                    LocationArguments(location = location)
                )
        }

        data class ShowStructural(val structural: StructuralParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = TransitComposeFragmentDirections.toStructural(
                    StructuralArguments(structural = structural, isCanEdit = true)
                )
        }

        data class ShowAccountingObjects(
            val params: List<ParamDomain>,
            val selectedAccountingObjectIds: List<String>
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = TransitComposeFragmentDirections.toAccountingObjects(
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
                get() = TransitComposeFragmentDirections.toReserves(
                    ReservesArguments(
                        params = params,
                        selectedReservesIds = selectedReserveIds,
                        isFromDocument = true
                    )
                )
        }

        data class ShowParamSteps(
            val currentStep: Int,
            val params: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = TransitComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        params = params,
                        currentStep = currentStep
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
    }
}