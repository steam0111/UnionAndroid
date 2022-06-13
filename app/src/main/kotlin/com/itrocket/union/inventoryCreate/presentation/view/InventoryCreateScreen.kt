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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateStore
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseCheckbox
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.InventoryDocumentItem
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb6
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
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    onSaveClickListener = onSaveClickListener,
                    onReadingClickListener = onReadingClickListener
                )
            },
            content = {
                Content(
                    state = state,
                    paddingValues = it,
                    onAddNewChanged = onAddNewChanged,
                    onHideFoundAccountingObjectChanged = onHideFoundAccountingObjectChanged,
                    onAccountingObjectClickListener = onAccountingObjectClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }
}

@Composable
private fun Content(
    state: InventoryCreateStore.State,
    paddingValues: PaddingValues,
    onAddNewChanged: () -> Unit,
    onHideFoundAccountingObjectChanged: () -> Unit,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit
) {
    val accountingObjectList = state.newAccountingObjects + state.accountingObjects

    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        InventoryDocumentItem(item = state.inventoryDocument)
        MediumSpacer()
        SettingsBar(
            isHideFoundAccountingObjects = state.isHideFoundAccountingObjects,
            isAddNew = state.isAddNew,
            onAddNewChanged = onAddNewChanged,
            onHideFoundAccountingObjectChanged = onHideFoundAccountingObjectChanged
        )
        MediumSpacer()
        LazyColumn {
            itemsIndexed(items = accountingObjectList, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = accountingObjectList.lastIndex != index
                AccountingObjectItem(
                    accountingObject = item,
                    onAccountingObjectListener = onAccountingObjectClickListener,
                    isShowBottomLine = isShowBottomLine,
                    status = item.inventoryStatus
                )
            }
        }
    }
}

@Composable
fun SettingsBar(
    isHideFoundAccountingObjects: Boolean,
    isAddNew: Boolean,
    onAddNewChanged: () -> Unit,
    onHideFoundAccountingObjectChanged: () -> Unit,
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
                onCheckClickListener = onHideFoundAccountingObjectChanged
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
                onCheckClickListener = onAddNewChanged
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
private fun BottomBar(
    onSaveClickListener: () -> Unit,
    onReadingClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.common_reading),
            onClick = onReadingClickListener,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        BaseButton(
            text = stringResource(R.string.common_save),
            onClick = onSaveClickListener,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
) {
    BaseToolbar(
        title = stringResource(id = R.string.inventory_ao_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        backgroundColor = psb1,
        textColor = white,
        content = {
            Text(
                text = stringResource(R.string.common_drop),
                style = AppTheme.typography.body2,
                color = psb6,
                modifier = Modifier.clickable(onClick = onDropClickListener)
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
    InventoryCreateScreen(InventoryCreateStore.State(
        inventoryDocument = InventoryCreateDomain(
            number = "БП-00001374",
            time = "12:40",
            date = "12.12.12",
            documentInfo = listOf(
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
            )
        ),
        accountingObjects = listOf(
            AccountingObjectDomain(
                id = "7",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung2",
                status = ObjectStatus.AVAILABLE,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            ),
            AccountingObjectDomain(
                id = "8",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung2",
                status = ObjectStatus.AVAILABLE,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )
    ), AppInsets(previewTopInsetDp), {}, {}, {}, {}, {}, {}, {})
}