package com.itrocket.union.ui.documents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStoreFactory
import com.itrocket.union.documentCreate.presentation.view.DocumentConfirmAlertType
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ConfirmAlertDialog
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.ImageButton
import com.itrocket.union.ui.Loader
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.OutlinedImageButton
import com.itrocket.union.ui.ReservesItem
import com.itrocket.union.ui.SelectedBaseField
import com.itrocket.union.ui.TabIndicatorBlack
import com.itrocket.union.ui.UnselectedBaseField
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentCreateBaseScreen(
    confirmDialogType: DocumentConfirmAlertType,
    params: List<ParamDomain>,
    selectedPage: Int,
    documentStatus: DocumentStatus,
    accountingObjectList: List<AccountingObjectDomain>,
    isLoading: Boolean,
    reserves: List<ReservesDomain>,
    documentType: DocumentTypeDomain,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onPageChanged: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit,
    onSettingsClickListener: () -> Unit,
    onChooseAccountingObjectClickListener: () -> Unit,
    onChooseReserveClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    onReserveClickListener: (ReservesDomain) -> Unit,
    onConfirmActionClick: () -> Unit,
    onDismissConfirmDialog: () -> Unit
) {
    val pagerState = rememberPagerState(selectedPage)
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.inventory_params),
            screen = {
                DocumentParamContent(
                    onParamClickListener = onParamClickListener,
                    params = params,
                    onCrossClickListener = onParamCrossClickListener,
                    onSaveClickListener = onSaveClickListener,
                    documentStatus = documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.document_create_accounting_object),
            screen = {
                DocumentAccountingObjectScreen(
                    isLoading = isLoading,
                    accountingObjectList = accountingObjectList,
                    onAccountingObjectClickListener = {},
                    onSaveClickListener = onSaveClickListener,
                    onSettingsClickListener = onSettingsClickListener,
                    onChooseClickListener = onChooseAccountingObjectClickListener,
                    documentStatus = documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.document_create_reserves),
            screen = {
                DocumentReservesScreen(
                    isLoading = isLoading,
                    reserves = reserves,
                    onReservesClickListener = onReserveClickListener,
                    onSaveClickListener = onSaveClickListener,
                    onSettingsClickListener = onSettingsClickListener,
                    onChooseClickListener = onChooseReserveClickListener,
                    documentStatus = documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        )
    )

    AppTheme {
        Scaffold(
            topBar = {
                DocumentToolbar(
                    documentType = documentType,
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener
                )
            },
            content = {
                DocumentContent(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = selectedPage,
                    coroutineScope = coroutineScope,
                    tabs = tabs
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { onPageChanged(it) }
    }

    // todo сделать через side effect
    if (confirmDialogType == DocumentConfirmAlertType.SAVE) {
        ConfirmAlertDialog(
            onDismiss = onDismissConfirmDialog,
            onConfirmClick = onConfirmActionClick,
            textRes = R.string.common_confirm_save_text
        )
    } else if (confirmDialogType == DocumentConfirmAlertType.CONDUCT) {
        ConfirmAlertDialog(
            onDismiss = onDismissConfirmDialog,
            onConfirmClick = onConfirmActionClick,
            textRes = R.string.confirm_conduct_text
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentContent(
    onTabClickListener: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    selectedPage: Int,
    pagerState: PagerState,
    tabs: List<BaseTab>
) {
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
                TabIndicatorBlack(tabPositions = it, pagerState = pagerState)
            }
        )
        MediumSpacer()
        HorizontalPager(count = tabs.size, state = pagerState, modifier = Modifier) { page ->
            tabs[page].screen()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentParamContent(
    params: List<ParamDomain>,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus,
    onParamClickListener: (ParamDomain) -> Unit,
    onCrossClickListener: (ParamDomain) -> Unit,
    onSaveClickListener: () -> Unit,
) {
    Scaffold(bottomBar = {
        DocumentParamBottomBar(
            onSaveClickListener = onSaveClickListener,
            onConductClickListener = onConductClickListener,
            documentStatus = documentStatus
        )
    }, content = {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
        ) {
            items(params, key = {
                it.type
            }) {
                if (it.value.isNotBlank()) {
                    SelectedBaseField(
                        label = stringResource(it.type.titleId),
                        value = it.value,
                        onFieldClickListener = {
                            onParamClickListener(it)
                        },
                        isCrossVisible = it.isClickable,
                        onCrossClickListener = {
                            onCrossClickListener(it)
                        }
                    )
                } else {
                    UnselectedBaseField(
                        label = stringResource(it.type.titleId),
                        clickable = it.isClickable,
                        onFieldClickListener = {
                            onParamClickListener(it)
                        })
                }
            }
        }
    })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentAccountingObjectScreen(
    isLoading: Boolean,
    accountingObjectList: List<AccountingObjectDomain>,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onSettingsClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onChooseClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus,
) {
    Scaffold(
        bottomBar = {
            DocumentListBottomBar(
                isAccountingObject = true,
                onSettingsClickListener = onSettingsClickListener,
                onSaveClickListener = onSaveClickListener,
                onChooseClickListener = onChooseClickListener,
                onConductClickListener = onConductClickListener,
                documentStatus = documentStatus
            )
        }, content = {
            when {
                isLoading -> {
                    Loader(contentPadding = PaddingValues())
                }
                accountingObjectList.isEmpty() -> {
                    DocumentEmpty(paddingValues = PaddingValues())
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        itemsIndexed(accountingObjectList, key = { index, item ->
                            item.id
                        }) { index, item ->
                            val isShowBottomLine = accountingObjectList.lastIndex != index
                            AccountingObjectItem(
                                accountingObject = item,
                                onAccountingObjectListener = onAccountingObjectClickListener,
                                isShowBottomLine = isShowBottomLine,
                                status = item.status?.type
                            )
                        }
                    }
                }
            }
        })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentReservesScreen(
    isLoading: Boolean,
    reserves: List<ReservesDomain>,
    onReservesClickListener: (ReservesDomain) -> Unit,
    onSettingsClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onChooseClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus,
) {
    Scaffold(
        bottomBar = {
            DocumentListBottomBar(
                isAccountingObject = false,
                onSettingsClickListener = onSettingsClickListener,
                onSaveClickListener = onSaveClickListener,
                onChooseClickListener = onChooseClickListener,
                onConductClickListener = onConductClickListener,
                documentStatus = documentStatus
            )
        }, content = {
            when {
                isLoading -> {
                    Loader(contentPadding = PaddingValues())
                }
                reserves.isEmpty() -> {
                    DocumentEmpty(paddingValues = PaddingValues())
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        itemsIndexed(reserves, key = { index, item ->
                            item.id
                        }) { index, item ->
                            val isShowBottomLine = reserves.lastIndex != index
                            ReservesItem(
                                reserves = item,
                                onReservesListener = onReservesClickListener,
                                isShowBottomLine = isShowBottomLine
                            )
                        }
                    }
                }
            }
        })
}

@Composable
fun DocumentToolbar(
    documentType: DocumentTypeDomain,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
) {
    BaseToolbar(
        title = stringResource(id = documentType.titleId),
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

@Composable
fun DocumentEmpty(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_reader),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                graphite4
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.document_create_empty),
            style = AppTheme.typography.body1,
            color = graphite4,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentParamBottomBar(
    onSaveClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus
) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        BaseButton(
            text = stringResource(id = R.string.common_save),
            onClick = onSaveClickListener,
            modifier = Modifier.weight(1f),
            enabled = documentStatus != DocumentStatus.COMPLETED
        )
        Spacer(modifier = Modifier.width(16.dp))
        BaseButton(
            text = stringResource(R.string.common_conduct),
            onClick = onConductClickListener,
            modifier = Modifier.weight(1f),
            enabled = documentStatus != DocumentStatus.COMPLETED
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentListBottomBar(
    isAccountingObject: Boolean,
    onSettingsClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onChooseClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus
) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        if (isAccountingObject) {
            ImageButton(
                imageId = R.drawable.ic_settings,
                paddings = PaddingValues(12.dp),
                onClick = onSettingsClickListener,
                isEnabled = documentStatus != DocumentStatus.COMPLETED
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        BaseButton(
            text = stringResource(R.string.common_choose),
            onClick = onChooseClickListener,
            modifier = Modifier.weight(1f),
            enabled = documentStatus != DocumentStatus.COMPLETED
        )
        Spacer(modifier = Modifier.width(8.dp))
        BaseButton(
            text = stringResource(R.string.common_conduct),
            onClick = onConductClickListener,
            modifier = Modifier.weight(1f),
            enabled = documentStatus != DocumentStatus.COMPLETED
        )
        Spacer(modifier = Modifier.width(8.dp))
        ImageButton(
            imageId = R.drawable.ic_save,
            paddings = PaddingValues(12.dp),
            onClick = onSaveClickListener,
            isEnabled = documentStatus != DocumentStatus.COMPLETED
        )
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun DocumentCreateBaseScreenPreview() {
    DocumentCreateBaseScreen(
        params = listOf(
            ParamDomain(
                "1", "fsdsfsdf",
                type = ManualType.STRUCTURAL_FROM
            ),
            ParamDomain(
                "1", "fsdsfsdf",
                type = ManualType.STRUCTURAL_TO
            ),
            ParamDomain(
                "1", "fsdsfsdf",
                type = ManualType.MOL
            ),
            ParamDomain(
                "1", "fsdsfsdf",
                type = ManualType.LOCATION
            ),
        ),
        selectedPage = 0,
        documentStatus = DocumentStatus.CREATED,
        accountingObjectList = listOf(),
        isLoading = false,
        reserves = listOf(),
        documentType = DocumentTypeDomain.WRITE_OFF,
        appInsets = AppInsets(topInset = previewTopInsetDp),
        onBackClickListener = {},
        onDropClickListener = {},
        onSaveClickListener = {},
        onPageChanged = {},
        onParamClickListener = {},
        onParamCrossClickListener = {},
        onSettingsClickListener = {},
        onChooseAccountingObjectClickListener = {},
        onChooseReserveClickListener = {},
        onConductClickListener = {},
        onReserveClickListener = {},
        confirmDialogType = DocumentConfirmAlertType.NONE,
        onConfirmActionClick = {},
        onDismissConfirmDialog = {}
    )
}