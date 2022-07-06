package com.itrocket.union.identify.presentation.store

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.*
import com.itrocket.core.navigation.ShowBottomSheetNavigationLabel
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuArguments
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuTab
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentDirections
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.inventories.presentation.view.InventoriesComposeFragmentDirections
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.readingMode.presentation.store.ReadingModeArguments
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailArguments
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        data class OnOSClicked(val item: AccountingObjectDomain) : Intent()
        data class OnReservesClicked(val item: ReservesDomain) : Intent()

        data class OnSelectPage(val selectedPage: Int) : IdentifyStore.Intent()
        data class OnParamClicked(val param: ParamDomain) : IdentifyStore.Intent()
        object OnDropClicked : IdentifyStore.Intent()
        object OnSaveClicked : IdentifyStore.Intent()
        object OnReadingModeClicked : Intent()
        data class OnItemClicked(val item: AccountingObjectDomain) : Intent()

        object OnFilterClicked : IdentifyStore.Intent()
        object OnBackClicked : IdentifyStore.Intent()
        object OnSearchClicked : IdentifyStore.Intent()
    }

    data class State(
//        val accountingObjectDomain: AccountingObjectDomain,

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
                listMainInfo = listOf()
            ),
            AccountingObjectDomain(
                id = "5",
                title = "Name2",
                status = null,
                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
                listAdditionallyInfo = listOf(),
                listMainInfo = listOf()
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
        object GoBack : Label(), GoBackNavigationLabel
        object ShowSave : Label()

        data class ShowReadingMode(val readingMode: ReadingModeTab) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    ReadingModeComposeFragment.READING_MODE_ARGS to ReadingModeArguments(readingMode)
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = ReadingModeComposeFragment()
        }

        class ShowDetail() : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to BottomActionMenuArguments()
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = BottomActionMenuFragment()
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
        data class ShowFilter(val filters: List<FilterDomain>) : IdentifyStore.Label(),
            ForwardNavigationLabel {
            override val directions: NavDirections
                get() = IdentifyComposeFragmentDirections.toFilter(FilterArguments(filters))
        }

//        data class ShowDetail(val item: ReservesDomain) :
//            Label(), ForwardNavigationLabel {
//            override val directions: NavDirections
//                get() = IdentifyComposeFragmentDirections.toReserveDetail(
//                    ReserveDetailArguments(argument = item)
//                )
//        }
    }
}

interface ShowIdentifyBottomBar : NavigationLabel {
    val fragment: Fragment
    val arguments: Bundle
    val containerId: Int
}