package com.itrocket.union.inventoryCreate.presentation.view

import androidx.annotation.StringRes
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventoryCreate.domain.InventoryPage
import com.itrocket.union.inventoryCreate.domain.entity.AccountingObjectCounter
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

data class InventoryCreateUiState(
    val inventoryDocument: InventoryCreateDomain,
    val isHideFoundAccountingObjects: Boolean = false,
    val isAddNew: Boolean = false,
    val isLoading: Boolean = false,
    val dialogType: AlertType = AlertType.NONE,
    val readingModeTab: ReadingModeTab,
    val canUpdate: Boolean = false,
    val canComplete: Boolean = false,
    val searchText: String = "",
    val isShowSearch: Boolean = false,
    val isDynamicSaveInventory: Boolean = false,
    val isCompleteLoading: Boolean = false,
    val canChangeInventory: Boolean = false,
    val saveEnabled: Boolean = false,
    val completeEnabled: Boolean = false,
    val dropEnabled: Boolean = false,
    val showComplete: Boolean = false,
    val isExistNonMarkingAccountingObject: Boolean = false,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val inventoryNomenclatures: List<InventoryNomenclatureDomain> = listOf(),
    val accountingObjectCounter: AccountingObjectCounter = AccountingObjectCounter(),
    val selectedPage: InventoryPage = InventoryPage.ACCOUNTING_OBJECT,
    @StringRes val loadingTitleId: Int = R.string.inventory_save_loading,
    val readerPower: Int? = null
)