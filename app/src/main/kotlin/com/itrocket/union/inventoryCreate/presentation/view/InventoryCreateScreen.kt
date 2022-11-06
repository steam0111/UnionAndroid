package com.itrocket.union.inventoryCreate.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseCheckbox
import com.itrocket.union.ui.ConfirmAlertDialog
import com.itrocket.union.ui.InventoryDocumentItem
import com.itrocket.union.ui.LoadingDialog
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.union.ui.black_50
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.white

@Composable
fun InventoryCreateScreen(
    state: InventoryCreateStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onReadingClickListener: () -> Unit,
    onAddNewChanged: () -> Unit,
    onHideFoundAccountingObjectChanged: () -> Unit,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onFinishClickListener: () -> Unit,
    onSaveConfirmClickListener: () -> Unit,
    onSaveDismissClickListener: () -> Unit,
    onCompleteConfirmClickListener: () -> Unit,
    onCompleteDismissClickListener: () -> Unit,
    onDeleteConfirmClickListener: () -> Unit,
    onDeleteDismissClickListener: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClickListener: () -> Unit,
    onStatusClickListener: (AccountingObjectDomain) -> Unit,
    onAccountingObjectLongClickListener: (AccountingObjectDomain) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener,
                    inventoryStatus = state.inventoryDocument.inventoryStatus,
                    onSearchClickListener = onSearchClickListener,
                    onSearchTextChanged = onSearchTextChanged,
                    searchText = state.searchText,
                    isShowSearch = state.isShowSearch,
                    canUpdate = state.canUpdate,
                )
            },
            bottomBar = {
                ReadingModeBottomBar(
                    readingModeTab = state.readingModeTab,
                    onReadingModeClickListener = onReadingClickListener
                )
            },
            content = {
                Content(
                    state = state,
                    paddingValues = it,
                    onAddNewChanged = onAddNewChanged,
                    onHideFoundAccountingObjectChanged = onHideFoundAccountingObjectChanged,
                    onAccountingObjectClickListener = onAccountingObjectClickListener,
                    onFinishClickListener = onFinishClickListener,
                    onSaveClickListener = onSaveClickListener,
                    onStatusClickListener = onStatusClickListener,
                    onAccountingObjectLongClickListener = onAccountingObjectLongClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
        when (state.dialogType) {
            AlertType.SAVE -> ConfirmAlertDialog(
                onDismiss = onSaveDismissClickListener,
                onConfirmClick = onSaveConfirmClickListener,
                textRes = R.string.common_confirm_save_text
            )
            AlertType.COMPLETE -> ConfirmAlertDialog(
                onDismiss = onCompleteDismissClickListener,
                onConfirmClick = onCompleteConfirmClickListener,
                textRes = R.string.inventory_complete_dialog
            )
            AlertType.LOADING -> LoadingDialog(title = stringResource(R.string.inventory_confirm_loading))
            AlertType.DELETE -> ConfirmAlertDialog(
                onDismiss = onDeleteDismissClickListener,
                onConfirmClick = onDeleteConfirmClickListener,
                textRes = R.string.common_ao_delete,
                confirmTextRes = R.string.common_yes,
                dismissTextRes = R.string.common_no
            )
        }
    }
}

private fun getAccountingObjects(state: InventoryCreateStore.State): List<AccountingObjectDomain> {
    return when {
        state.isShowSearch -> state.searchAccountingObjects
        state.isHideFoundAccountingObjects -> {
            val list =
                state.newAccountingObjects.toList() + state.inventoryDocument.accountingObjects
            list.filter { it.inventoryStatus != InventoryAccountingObjectStatus.FOUND }
        }
        else -> state.newAccountingObjects.toList() + state.inventoryDocument.accountingObjects
    }
}

@Composable
private fun Content(
    state: InventoryCreateStore.State,
    paddingValues: PaddingValues,
    onAddNewChanged: () -> Unit,
    onHideFoundAccountingObjectChanged: () -> Unit,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onFinishClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onStatusClickListener: (AccountingObjectDomain) -> Unit,
    onAccountingObjectLongClickListener: (AccountingObjectDomain) -> Unit
) {
    val accountingObjectList = getAccountingObjects(state)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            LazyColumn {
                item {
                    if (state.inventoryDocument.inventoryStatus != InventoryStatus.COMPLETED && state.canUpdate) {
                        InventoryBottomBar(
                            onSaveClickListener = onSaveClickListener,
                            onFinishClickListener = onFinishClickListener,
                            inventoryStatus = state.inventoryDocument.inventoryStatus,
                            canUpdate = state.canUpdate,
                            canComplete = state.canComplete,
                            isDynamicSaveInventory = state.isDynamicSaveInventory,
                            isAccountingObjectLoading = state.isLoading,
                            isCompleteLoading = state.isCompleteLoading
                        )
                    }
                    InventoryDocumentItem(item = state.inventoryDocument, isShowStatus = false)
                    MediumSpacer()
                    CountBar(
                        allAccountingObjects = state.accountingObjectCounter.total,
                        findAccountingObjects = state.accountingObjectCounter.found,
                        notFindAccountingObjects = state.accountingObjectCounter.notFound,
                        newAccountingObjects = state.accountingObjectCounter.new
                    )
                    MediumSpacer()
                    SettingsBar(
                        isHideFoundAccountingObjects = state.isHideFoundAccountingObjects,
                        isAddNew = state.isAddNew,
                        onAddNewChanged = onAddNewChanged,
                        onHideFoundAccountingObjectChanged = onHideFoundAccountingObjectChanged,
                        canUpdate = state.canUpdate
                    )
                    MediumSpacer()
                }
                itemsIndexed(items = accountingObjectList, key = { index, item ->
                    item.id
                }) { index, item ->
                    val isShowBottomLine = accountingObjectList.lastIndex != index
                    AccountingObjectItem(
                        accountingObject = item,
                        onAccountingObjectListener = onAccountingObjectClickListener,
                        onStatusClickListener = { onStatusClickListener(item) },
                        isShowBottomLine = isShowBottomLine,
                        status = item.inventoryStatus,
                        isEnabled = state.inventoryDocument.inventoryStatus != InventoryStatus.COMPLETED && state.canUpdate,
                        onAccountingObjectLongClickListener = onAccountingObjectLongClickListener
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
                }
            }
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(black_50),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CountBar(
    allAccountingObjects: Int,
    findAccountingObjects: Int,
    notFindAccountingObjects: Int,
    newAccountingObjects: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.inventory_create_total_ao, allAccountingObjects),
                style = AppTheme.typography.body2
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.inventory_create_find_ao, findAccountingObjects),
                style = AppTheme.typography.body2
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.inventory_create_new_ao, newAccountingObjects),
                style = AppTheme.typography.body2
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(
                    R.string.inventory_create_not_find_ao,
                    notFindAccountingObjects
                ),
                style = AppTheme.typography.body2
            )
        }
    }
}

@Composable
fun SettingsBar(
    isHideFoundAccountingObjects: Boolean,
    isAddNew: Boolean,
    onAddNewChanged: () -> Unit,
    onHideFoundAccountingObjectChanged: () -> Unit,
    canUpdate: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BaseCheckbox(
                isChecked = isHideFoundAccountingObjects,
                onCheckClickListener = onHideFoundAccountingObjectChanged,
                enabled = canUpdate
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.inventory_create_hide_found_ao),
                style = AppTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BaseCheckbox(
                isChecked = isAddNew,
                onCheckClickListener = onAddNewChanged,
                enabled = canUpdate
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.inventory_create_add_new),
                style = AppTheme.typography.body2
            )
        }
    }
}

@Composable
fun InventoryBottomBar(
    onSaveClickListener: () -> Unit,
    onInWorkClickListener: () -> Unit = {},
    onFinishClickListener: () -> Unit,
    inventoryStatus: InventoryStatus,
    canUpdate: Boolean,
    canComplete: Boolean = false,
    isDynamicSaveInventory: Boolean,
    isAccountingObjectLoading: Boolean,
    isCompleteLoading: Boolean,
) {
    Row(
        modifier = Modifier
            .background(graphite2)
            .padding(16.dp)
    ) {
        if (!isDynamicSaveInventory) {
            BaseButton(
                enabled = inventoryStatus != InventoryStatus.COMPLETED && canUpdate && !isAccountingObjectLoading,
                text = stringResource(R.string.common_save),
                onClick = onSaveClickListener,
                modifier = Modifier.weight(1f),
                disabledBackgroundColor = AppTheme.colors.secondaryColor
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        when (inventoryStatus) {
            InventoryStatus.CREATED -> {
                BaseButton(
                    enabled = canUpdate && !isAccountingObjectLoading,
                    onClick = onInWorkClickListener,
                    modifier = Modifier.weight(1f),
                    disabledBackgroundColor = AppTheme.colors.secondaryColor,
                    text = stringResource(R.string.common_in_work),
                )
            }
            InventoryStatus.IN_PROGRESS -> {
                if (canComplete) {
                    BaseButton(
                        text = stringResource(R.string.common_complete),
                        onClick = onFinishClickListener,
                        modifier = Modifier.weight(1f),
                        enabled = canUpdate && !isAccountingObjectLoading && !isCompleteLoading,
                        disabledBackgroundColor = AppTheme.colors.secondaryColor
                    )
                }
            }
            InventoryStatus.COMPLETED -> {
                //Nothing
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onSearchClickListener: (() -> Unit),
    onSearchTextChanged: ((String) -> Unit),
    searchText: String,
    isShowSearch: Boolean,
    inventoryStatus: InventoryStatus,
    canUpdate: Boolean
) {
    SearchToolbar(
        title = stringResource(id = R.string.inventory_ao_title),
        onBackClickListener = onBackClickListener,
        onSearchTextChanged = onSearchTextChanged,
        onSearchClickListener = onSearchClickListener,
        searchText = searchText,
        isShowSearch = isShowSearch,
        content = {
            Spacer(modifier = Modifier.width(28.dp))
            Text(
                text = stringResource(R.string.common_drop),
                style = AppTheme.typography.body2,
                color = AppTheme.colors.mainColor,
                modifier = Modifier.clickable(
                    onClick = onDropClickListener,
                    enabled = inventoryStatus == InventoryStatus.CREATED && canUpdate
                )
            )
        }
    )
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun InventoryCreateScreenPreview() {
    InventoryCreateScreen(
        InventoryCreateStore.State(
            inventoryDocument = InventoryCreateDomain(
                id = "",
                number = "БП-00001374",
                creationDate = System.currentTimeMillis(),
                documentInfo = listOf(
                    StructuralParamDomain(manualType = ManualType.STRUCTURAL),
                    ParamDomain("2", "Систмный интегратор", ManualType.MOL),
                ),
                accountingObjects = listOf(
                    AccountingObjectDomain(
                        id = "7",
                        isBarcode = true,
                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
                        status = ObjectStatus("Доступен"),
                        listMainInfo = listOf(
                            ObjectInfoDomain(
                                R.string.auth_main_title,
                                "таылватвлыавыалвыоалвыа"
                            ),
                            ObjectInfoDomain(
                                R.string.auth_main_title,
                                "таылватвлыавыалвыоалвыа"
                            ),
                        ),
                        listAdditionallyInfo = listOf(),
                        barcodeValue = "",
                        rfidValue = "",
                        factoryNumber = ""
                    ),
                    AccountingObjectDomain(
                        id = "8",
                        isBarcode = true,
                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
                        status = ObjectStatus("Доступен"),
                        listMainInfo = listOf(
                            ObjectInfoDomain(
                                R.string.auth_main_title,
                                "таылватвлыавыалвыоалвыа"
                            ),
                            ObjectInfoDomain(
                                R.string.auth_main_title,
                                "таылватвлыавыалвыоалвыа"
                            ),
                        ),
                        listAdditionallyInfo = listOf(),
                        barcodeValue = "",
                        rfidValue = "",
                        factoryNumber = ""
                    ),
                ),
                inventoryStatus = InventoryStatus.CREATED,
                userInserted = "",
                userUpdated = ""
            ),
            readingModeTab = ReadingModeTab.RFID
        ),
        AppInsets(previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
    )
}

