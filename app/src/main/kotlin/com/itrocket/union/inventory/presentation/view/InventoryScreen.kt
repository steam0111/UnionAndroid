package com.itrocket.union.inventory.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.inventoryCreate.presentation.view.AttentionNotMarking
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.ConfirmAlertDialog
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.InventoryNomenclatureItem
import com.itrocket.union.ui.LoadingDialog
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.SelectedBaseField
import com.itrocket.union.ui.TabRowIndicator
import com.itrocket.union.ui.UnselectedBaseField
import com.itrocket.union.ui.graphite2
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun InventoryScreen(
    state: InventoryStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onInventoryCreateClickListener: () -> Unit,
    onPageChanged: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit,
    onSaveClickListener: () -> Unit,
    onInWorkClickListener: () -> Unit,
    onSaveConfirmClickListener: () -> Unit,
    onSaveDismissClickListener: () -> Unit,
    onInWorkConfirmClickListener: () -> Unit,
    onInWorkDismissClickListener: () -> Unit,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onInventoryNomenclatureClickListener: (InventoryNomenclatureDomain) -> Unit,
    onExitConfirmClickListener: () -> Unit,
    onAlertDismissClickListener: () -> Unit,
    onDropConfirmedClickListener: () -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()
    val isInventoryChangePermitted = if (state.inventoryCreateDomain != null) {
        state.canUpdateInventory
    } else {
        state.canCreateInventory
    }

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener,
                    isInventoryChangePermitted = isInventoryChangePermitted
                )
            },
            bottomBar = {
                when {
                    state.inventoryCreateDomain != null -> {
                        InventoryBottomBar(
                            onSaveClickListener = onSaveClickListener,
                            onInWorkClickListener = onInWorkClickListener,
                            inventoryStatus = state.inventoryCreateDomain.inventoryStatus,
                            canUpdate = state.canUpdateInventory,
                            isDynamicSaveInventory = state.isDynamicSaveInventory,
                            isAccountingObjectLoading = state.isInventoryObjectsLoading,
                        )
                    }
                    state.canCreateInventory -> {
                        ButtonBottomBar(
                            onClick = onInventoryCreateClickListener,
                            text = stringResource(R.string.common_create),
                            isLoading = state.isCreateInventoryLoading,
                            isEnabled = !state.isCreateInventoryLoading
                        )
                    }
                }
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    onParamCrossClickListener = onParamCrossClickListener,
                    onParamClickListener = onParamClickListener,
                    paddingValues = it,
                    state = state,
                    isInventoryChangePermitted = isInventoryChangePermitted,
                    onAccountingObjectClickListener = onAccountingObjectClickListener,
                    isExistNonMarkingAccountingObject = state.isExistNonMarkingAccountingObject,
                    onInventoryNomenclatureClickListener = onInventoryNomenclatureClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
        when (state.dialogType) {
            AlertType.SAVE -> {
                ConfirmAlertDialog(
                    onDismiss = onSaveDismissClickListener,
                    onConfirmClick = onSaveConfirmClickListener,
                    textRes = R.string.common_confirm_save_text
                )
            }
            AlertType.IN_WORK -> {
                ConfirmAlertDialog(
                    onDismiss = onInWorkDismissClickListener,
                    onConfirmClick = onInWorkConfirmClickListener,
                    textRes = R.string.inventory_in_work_dialog
                )
            }
            AlertType.EXIT -> {
                ConfirmAlertDialog(
                    onDismiss = onAlertDismissClickListener,
                    onConfirmClick = onExitConfirmClickListener,
                    textRes = R.string.inventory_exit_confirm
                )
            }
            AlertType.DROP -> {
                ConfirmAlertDialog(
                    onDismiss = onAlertDismissClickListener,
                    onConfirmClick = onDropConfirmedClickListener,
                    textRes = R.string.inventory_drop_confirm
                )
            }
            AlertType.LOADING -> {
                LoadingDialog(
                    title = getLoadingTitle(
                        isCreateLoading = state.isCreateInventoryLoading,
                        isInWorkLoading = state.isInWorkInventoryLoading
                    )
                )
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onPageChanged(it)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(
    state: InventoryStore.State,
    onTabClickListener: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onInventoryNomenclatureClickListener: (InventoryNomenclatureDomain) -> Unit,
    coroutineScope: CoroutineScope,
    selectedPage: Int,
    pagerState: PagerState,
    paddingValues: PaddingValues,
    isInventoryChangePermitted: Boolean,
    isExistNonMarkingAccountingObject: Boolean
) {
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.inventory_params),
            screen = {
                ParamContent(
                    paddingValues = paddingValues,
                    onParamClickListener = onParamClickListener,
                    params = state.params,
                    onCrossClickListener = onParamCrossClickListener,
                    isInventoryChangePermitted = isInventoryChangePermitted
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.inventory_accounting_object),
            screen = {
                AccountingObjectScreen(
                    isLoading = state.isInventoryObjectsLoading,
                    accountingObjectList = state.accountingObjectList,
                    onAccountingObjectClickListener = onAccountingObjectClickListener,
                    paddingValues = paddingValues
                )
            }
        ),
        BaseTab(
            title = stringResource(id = R.string.inventory_reserves),
            screen = {
                InventoryNomenclatureScreen(
                    isLoading = state.isInventoryObjectsLoading,
                    inventoryNomenclatures = state.inventoryNomenclatures,
                    onInventoryNomenclatureClickListener = onInventoryNomenclatureClickListener,
                    paddingValues = paddingValues
                )
            }
        )
    )
    Column {
        DoubleTabRow(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    color = graphite2,
                    shape = RoundedCornerShape(8.dp)
                ),
            selectedPage = selectedPage,
            targetPage = pagerState.getTargetPage(),
            tabs = tabs,
            onTabClickListener = {
                onTabClickListener(it)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            },
            tabIndicator = {
                TabRowIndicator(tabPositions = it, pagerState = pagerState)
            }
        )
        if (isExistNonMarkingAccountingObject) {
            AttentionNotMarking()
        } else {
            MediumSpacer()
        }
        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            tabs[page].screen()
        }
    }
}

@Composable
private fun ParamContent(
    params: List<ParamDomain>,
    onParamClickListener: (ParamDomain) -> Unit,
    onCrossClickListener: (ParamDomain) -> Unit,
    isInventoryChangePermitted: Boolean,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues)
    ) {
        items(params, key = {
            it.type
        }) {
            if (it.value.isNotBlank()) {
                SelectedBaseField(
                    label = stringResource(it.type.titleId),
                    value = it.value,
                    clickable = it.isClickable && isInventoryChangePermitted,
                    onFieldClickListener = {
                        onParamClickListener(it)
                    },
                    isCrossVisible = it.isClickable && isInventoryChangePermitted,
                    onCrossClickListener = {
                        onCrossClickListener(it)
                    }
                )
            } else {
                UnselectedBaseField(
                    label = stringResource(it.type.titleId),
                    clickable = it.isClickable && isInventoryChangePermitted,
                    onFieldClickListener = {
                        onParamClickListener(it)
                    })
            }
        }
    }
}


@Composable
private fun AccountingObjectScreen(
    isLoading: Boolean,
    accountingObjectList: List<AccountingObjectDomain>,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.inventory_ao_wait),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            itemsIndexed(accountingObjectList, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = accountingObjectList.lastIndex != index
                AccountingObjectItem(
                    accountingObject = item,
                    onAccountingObjectListener = onAccountingObjectClickListener,
                    isShowBottomLine = isShowBottomLine,
                    status = item.status?.type,
                    showNonMarkingAttention = true
                )
            }
        }
    }
}

@Composable
private fun InventoryNomenclatureScreen(
    isLoading: Boolean,
    inventoryNomenclatures: List<InventoryNomenclatureDomain>,
    onInventoryNomenclatureClickListener: (InventoryNomenclatureDomain) -> Unit,
    paddingValues: PaddingValues
) {
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.inventory_reserves_wait),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            itemsIndexed(inventoryNomenclatures, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = inventoryNomenclatures.lastIndex != index
                InventoryNomenclatureItem(
                    inventoryNomenclatureDomain = item,
                    onClick = onInventoryNomenclatureClickListener,
                    isShowBottomLine = isShowBottomLine,
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    isInventoryChangePermitted: Boolean
) {
    BaseToolbar(
        title = stringResource(id = R.string.inventory_ao_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        content = {
            Text(
                text = stringResource(R.string.common_drop),
                style = AppTheme.typography.body2,
                color = AppTheme.colors.mainColor,
                modifier = Modifier.clickable(
                    onClick = onDropClickListener,
                    enabled = isInventoryChangePermitted
                )
            )
        }
    )
}

@Composable
private fun InventoryBottomBar(
    onSaveClickListener: () -> Unit,
    onInWorkClickListener: () -> Unit = {},
    inventoryStatus: InventoryStatus,
    canUpdate: Boolean,
    isDynamicSaveInventory: Boolean,
    isAccountingObjectLoading: Boolean,
) {
    Row(
        modifier = Modifier
            .background(graphite2)
            .padding(16.dp)
    ) {
        if (!isDynamicSaveInventory) {
            BaseButton(
                enabled = canUpdate,
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
        }
    }
}

@Composable
private fun getLoadingTitle(
    isCreateLoading: Boolean,
    isInWorkLoading: Boolean
): String {
    val titleId = when {
        isCreateLoading -> R.string.inventory_create_loading
        isInWorkLoading -> R.string.inventory_in_work_loading
        else -> R.string.inventory_ao_wait
    }
    return stringResource(titleId)
}

@Preview(
    name = "?????????????? ???????? ?????????? - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "???????????? ???????? ?????????? - 4,95 (1920 ?? 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "??????????????", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun InventoryScreenPreview() {
    InventoryScreen(
        InventoryStore.State(
            params = listOf(
                StructuralParamDomain(manualType = ManualType.STRUCTURAL),
                ParamDomain(
                    "2",
                    "???????????????????? ?????????????? ???????????????????????????? ",
                    type = ManualType.MOL
                ),
                ParamDomain("3", "", type = ManualType.LOCATION_INVENTORY),
            ),
            accountingObjectList = listOf(
                AccountingObjectDomain(
                    id = "1",
                    isBarcode = true,
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("AVAILABLE"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                ),
                AccountingObjectDomain(
                    id = "2",
                    isBarcode = true,
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("AVAILABLE"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                ), AccountingObjectDomain(
                    id = "3",
                    isBarcode = true,
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("AVAILABLE"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                )
            ),
            selectedPage = 1,
            inventoryCreateDomain = null
        ),
        AppInsets(topInset = previewTopInsetDp),
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
        {}
    )
}