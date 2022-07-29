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
import com.itrocket.union.accountingObjects.presentation.view.AccountingObjectComposeFragmentDirections
import com.itrocket.union.bottomActionMenu.presentation.store.BottomActionMenuArguments
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuFragment
import com.itrocket.union.bottomActionMenu.presentation.view.BottomActionMenuTab
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.filter.presentation.store.FilterArguments
import com.itrocket.union.identify.presentation.view.IdentifyComposeFragmentDirections
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeComposeFragment
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface IdentifyStore : Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> {

    sealed class Intent {
        //Toolbar
        object OnReadingModeClicked : Intent()
        object OnDropClicked : IdentifyStore.Intent()
        object OnSaveClicked : IdentifyStore.Intent()
        data class OnSelectPage(val selectedPage: Int) : IdentifyStore.Intent()
        object OnFilterClicked : IdentifyStore.Intent()
        object OnBackClicked : IdentifyStore.Intent()
        object OnSearchClicked : IdentifyStore.Intent()

        data class OnItemClicked(val item: AccountingObjectDomain) : Intent()
        data class OnObjectActionSelected(val objectAction: ObjectAction) : Intent()
        data class OnDeleteFromBottomAction(val bottomActionResult: List<AccountingObjectDomain>) :
            Intent()

        data class OnNewAccountingObjectRfidsHandled(val rfids: List<String>) :
            Intent()

        data class OnNewAccountingObjectBarcodeHandled(val barcode: String) :
            Intent()

        data class OnAccountingObjectSelected(val accountingObjectDomain: AccountingObjectDomain) :
            Intent()
    }

    data class State(
        val isBottomActionMenuLoading: Boolean = false,

        val isIdentifyLoading: Boolean = false,
        val readingMode: ReadingModeTab = ReadingModeTab.BARCODE,
        val bottomActionMenuTab: BottomActionMenuTab = BottomActionMenuTab.CREATE,
        val os: List<AccountingObjectDomain> = listOf(),
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

        data class ShowDetail(
            val item: AccountingObjectDomain,
            val listAO: List<AccountingObjectDomain>
        ) : Label(),
            ShowBottomSheetNavigationLabel {
            override val arguments: Bundle
                get() = bundleOf(
                    BottomActionMenuFragment.BOTTOMACTIONMENU_ARGS to BottomActionMenuArguments(
                        bottomActionMenuDocument = item,
                        listAO = listAO
                    ),
                )

            override val containerId: Int = R.id.mainActivityNavHostFragment

            override val fragment: Fragment
                get() = BottomActionMenuFragment()
        }

        data class OpenCardOS(val item: AccountingObjectDomain) : Label(), ForwardNavigationLabel {
            override val directions: NavDirections
                get() = AccountingObjectComposeFragmentDirections.toAccountingObjectsDetails(
                    AccountingObjectDetailArguments(argument = item)
                )
        }

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
    }
}
