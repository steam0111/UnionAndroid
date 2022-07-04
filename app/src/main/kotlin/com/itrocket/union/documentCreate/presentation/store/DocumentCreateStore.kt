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
import com.itrocket.union.documentCreate.presentation.view.DocumentCreateComposeFragmentDirections
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.location.presentation.store.LocationArguments
import com.itrocket.union.location.presentation.store.LocationResult
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.store.ReservesArguments
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments

interface DocumentCreateStore :
    Store<DocumentCreateStore.Intent, DocumentCreateStore.State, DocumentCreateStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnSaveClicked : Intent()
        object OnNextClicked : Intent()
        object OnPrevClicked : Intent()
        object OnSettingsClicked : Intent()
        object OnChooseClicked : Intent()
        object OnCompleteClicked : Intent()
        data class OnSelectPage(val selectedPage: Int) : Intent()
        data class OnParamClicked(val param: ParamDomain) : Intent()
        data class OnParamCrossClicked(val param: ParamDomain) : Intent()
        data class OnParamsChanged(val params: List<ParamDomain>) : Intent()
        data class OnReserveSelected(val reserve: ReservesDomain) :
            Intent()
        data class OnAccountingObjectSelected(val accountingObjectDomain: AccountingObjectDomain) :
            Intent()

        data class OnNewAccountingObjectRfidsHandled(val rfids: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnLocationChanged(val location: LocationResult) : Intent()
    }

    data class State(
        val document: DocumentDomain,
        val accountingObjects: List<AccountingObjectDomain> = listOf(),
        val reserves: List<ReservesDomain> = listOf(),
        val objectType: ObjectType,
        val params: List<ParamDomain>,
        val isLoading: Boolean = false,
        val selectedPage: Int = 0,
        val isNextEnabled: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel

        data class ShowLocation(val location: LocationParamDomain) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toLocation(
                    LocationArguments(location = location)
                )
        }

        data class ShowAccountingObjects(
            val params: List<ParamDomain>,
            val selectedAccountingObjectIds: List<String>
        ) : Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toAccountingObjects(
                    AccountingObjectArguments(
                        params = params,
                        selectedAccountingObjectIds = selectedAccountingObjectIds
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
                        selectedReservesIds = selectedReserveIds
                    )
                )
        }

        data class ShowParamSteps(
            val currentStep: Int,
            val params: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentCreateComposeFragmentDirections.toSelectParams(
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
    }
}