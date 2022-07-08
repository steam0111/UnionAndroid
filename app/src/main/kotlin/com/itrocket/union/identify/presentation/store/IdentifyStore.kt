package com.itrocket.union.identify.presentation.store

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.changeAction
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.*
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuArguments
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuTab
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateArguments
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.documents.presentation.store.DocumentStore
import com.itrocket.union.documents.presentation.view.DocumentComposeFragmentDirections
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.identify.domain.entity.OSandReserves
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentDirections
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailArguments
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.view.ReservesComposeFragmentDirections

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        data class OnOSClicked(val item: AccountingObjectDomain) : Intent()
        data class OnReservesClicked(val item: OSandReserves) : Intent()
        data class OnObjectActionSelected(val objectAction: ObjectAction) : Intent()
        data class OnSelectPage(val selectedPage: Int) : IdentifyStore.Intent()
        object OnDropClicked : IdentifyStore.Intent()
        object OnSaveClicked : IdentifyStore.Intent()
        object OnReadingModeClicked : Intent()
        data class OnItemClicked(val item: AccountingObjectDomain) : Intent()
        data class OnOpenCard(val item: ReservesDomain) : Intent()

        object OnFilterClicked : IdentifyStore.Intent()
        object OnBackClicked : IdentifyStore.Intent()
        object OnSearchClicked : IdentifyStore.Intent()
    }

    data class State(
//        val accountingObjectDomain: AccountingObjectDomain,
        val isBottomActionMenuLoading: Boolean = false,

        val isIdentifyLoading: Boolean = false,
        val readingMode: ReadingModeTab = ReadingModeTab.RFID,
        val bottomActionMenuTab: BottomActionMenuTab = BottomActionMenuTab.CREATE,
        val identifies: List<AccountingObjectDomain>
        = listOf(
            AccountingObjectDomain(
                id = "4",
                title = "Name",
                status = null,
                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
                listAdditionallyInfo = listOf(),
                listMainInfo = listOf(),
                rfidValue = null,
                isBarcode = true,
                barcodeValue = null
            ),
            AccountingObjectDomain(
                id = "5",
                title = "Name2",
                status = null,
                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
                listAdditionallyInfo = listOf(),
                listMainInfo = listOf(),
                rfidValue = null,
                isBarcode = true,
                barcodeValue = null
            )
        ),
        val reserves: List<ReservesDomain>
        = listOf(
            ReservesDomain(
                id = "1",
                title = "Авторучка 'Зебра TR22'",
                comment = "",
                isBarcode = true,
                itemsCount = 1200,
                listInfo = listOf()
            ),
            ReservesDomain(
                id = "2",
                title = "Бумага А4 'Русалочка-500 листов'",
                comment = "",
                isBarcode = false,
                itemsCount = 56,
                listInfo = listOf()
            ),
            ReservesDomain(
                id = "3",
                title = "Бумага А4 'Русалочка-250 листов'",
                comment = "",
                isBarcode = true,
                itemsCount = 167,
                listInfo = listOf()
            ),
            ReservesDomain(
                id = "4",
                title = "Бумага А4 'Русалочка-150 листов'",
                comment = "",
                isBarcode = true,
                itemsCount = 5,
                listInfo = listOf()
            ),
            ReservesDomain(
                id = "5",
                title = "Авторучка 'Зебра TR11'",
                comment = "",
                isBarcode = true,
                itemsCount = 200,
                listInfo = listOf()
            ),
            ReservesDomain(
                id = "6",
                title = "Бумага А4 'Русалочка-1000 листов'",
                comment = "",
                isBarcode = true,
                itemsCount = 11,
                listInfo = listOf()
            ),
        ),
        val selectedPage: Int = 0,
//        val accountingObject: AccountingObjectDomain
//        = listOf(
//            AccountingObjectDomain(
//                id = "4",
//                title = "Name",
//                status = null,
//                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
//                listAdditionallyInfo = listOf(),
//                listMainInfo = listOf()
//            ),
//            AccountingObjectDomain(
//                id = "5",
//                title = "Name2",
//                status = null,
//                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
//                listAdditionallyInfo = listOf(),
//                listMainInfo = listOf()
//            ),
    )


    sealed class Label {
        data class ShowDocumentCreate(
            private val document: DocumentDomain
        ) : IdentifyStore.Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = DocumentComposeFragmentDirections.toDocumentCreate(
                    DocumentCreateArguments(
                        document = document
                    )
                )
        }

        object GoBack : Label(), GoBackNavigationLabel
        object ShowSave : Label()

        data class ShowReadingMode(val readingMode: ReadingModeTab) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf()

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        data class ShowDetail(val item: OSandReserves) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to item
//                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to BottomActionMenuArguments()
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = BottomActionMenuFragment()
        }

      data  class OpenCard(val item: OSandReserves) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = ReservesComposeFragmentDirections.toReserveDetail(
                    ReserveDetailArguments(id = item.id)
                )
        }


//        data class ShowDetail(val item: ReservesDomain) : Label(),
//            ShowIdentifyBottomBar {
//            override val fragment: Fragment
//                get() = BottomActionMenuFragment()
//            override val arguments: Bundle
//                get() = bundleOf(
//                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to BottomActionMenuArguments(item)
//                )
//            override val containerId: Int = R.id.mainActivityNavHostFragment
//        }

        data class Error(override val message: String) : Label(), DefaultNavigationErrorLabel
        data class ShowFilter(val filters: List<ParamDomain>) : IdentifyStore.Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toFilter(
                    FilterArguments(
                        filters,
                        CatalogType.IDENTIFIES
                    )
                )
        }

//        data class ShowDetail(val item: ReservesDomain) :
//            Label(), ForwardNavigationLabel {
//            override val directions: NavDirections
//                get() = IdentifyComposeFragmentDirections.toReserveDetail(
//                    ReserveDetailArguments(argument = item)
//                )
//        }
    }

    fun changeAction(item: ObjectAction) {
        when (item) {
            ObjectAction.CREATE_DOC -> {
                Log.d("SukhanovTest", "Click CREATE")

            }
            ObjectAction.DELETE_FROM_LIST -> {
                Log.d("SukhanovTest", "Click DELETE")
            }
            ObjectAction.OPEN_CARD -> {
                Log.d("SukhanovTest", "Click OPEN")
            }
        }
    }
}
