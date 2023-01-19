package com.itrocket.union.inventory.presentation.store

import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.DefaultNavigationErrorLabel
import com.itrocket.core.navigation.ForwardNavigationLabel
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_CODE
import com.itrocket.union.inventory.presentation.view.InventoryComposeFragment.Companion.INVENTORY_RESULT_LABEL
import com.itrocket.union.inventoryContainer.presentation.view.InventoryContainerComposeFragmentDirections
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.nomenclatureDetail.presentation.store.NomenclatureDetailArguments
import com.itrocket.union.selectParams.presentation.store.SelectParamsArguments

interface InventoryStore :
    Store<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
        object OnDropClicked : Intent()
        object OnCreateDocumentClicked : Intent()
        object OnInWorkClicked : Intent()
        object OnSaveClicked : Intent()
        object OnSaveConfirmed : Intent()
        object OnSaveDismissed : Intent()
        object OnInWorkConfirmed : Intent()
        object OnInWorkDismissed : Intent()
        object OnExitConfirmed : Intent()
        object OnAlertDismissed : Intent()
        object OnDropConfirmed : Intent()
        data class OnAccountingObjectClicked(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnInventoryNomenclatureClicked(val inventoryNomenclature: InventoryNomenclatureDomain) :
            Intent()

        data class OnAccountingObjectChanged(val accountingObject: AccountingObjectDomain) :
            Intent()

        data class OnSelectPage(val selectedPage: Int) : Intent()
        data class OnParamClicked(val param: ParamDomain) : Intent()
        data class OnParamCrossClicked(val param: ParamDomain) : Intent()
        data class OnParamsChanged(val params: List<ParamDomain>) : Intent()
    }

    data class State(
        val isAccountingObjectsLoading: Boolean = false,
        val isCreateInventoryLoading: Boolean = false,
        val isInWorkInventoryLoading: Boolean = false,
        val selectedPage: Int = 0,
        val accountingObjectList: List<AccountingObjectDomain> = listOf(),
        val inventoryNomenclatures: List<InventoryNomenclatureDomain> = listOf(),
        val params: List<ParamDomain> = listOf(
            StructuralParamDomain(manualType = ManualType.STRUCTURAL),
            StructuralParamDomain(manualType = ManualType.BALANCE_UNIT, clickable = false),
            ParamDomain(id = "", value = "", type = ManualType.MOL_IN_STRUCTURAL),
            LocationParamDomain(manualType = ManualType.LOCATION_INVENTORY),
            ParamDomain(id = "", value = "", type = ManualType.INVENTORY_BASE),
            ParamDomain(
                id = "",
                value = "",
                type = ManualType.INVENTORY_CHECKER,
                isClickable = false
            )
        ),
        val canCreateInventory: Boolean = false,
        val canUpdateInventory: Boolean = false,
        val inventoryCreateDomain: InventoryCreateDomain?,
        val isDynamicSaveInventory: Boolean = false,
        val dialogType: AlertType = AlertType.NONE,
        val isExistNonMarkingAccountingObject: Boolean = false,
    )

    sealed class Label {
        class GoBack(override val result: InventoryResult) : Label(), GoBackNavigationLabel {
            override val resultCode: String = INVENTORY_RESULT_CODE
            override val resultLabel: String = INVENTORY_RESULT_LABEL
        }

        data class ShowCreateInventory(
            val inventoryCreate: InventoryCreateDomain
        ) : Label()

        data class ShowParamSteps(
            val currentFilter: ParamDomain,
            val allParams: List<ParamDomain>
        ) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toSelectParams(
                    SelectParamsArguments(
                        currentFilter = currentFilter,
                        allParams = allParams
                    )
                )
        }

        data class ShowAccountingObjectDetail(val accountingObject: AccountingObjectDomain) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(accountingObject)
                )
        }

        data class ShowNomenclatureDetail(val nomenclatureId: String) :
            Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = InventoryContainerComposeFragmentDirections.toNomenclatureDetail(
                    NomenclatureDetailArguments(nomenclatureId)
                )
        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
    }
}