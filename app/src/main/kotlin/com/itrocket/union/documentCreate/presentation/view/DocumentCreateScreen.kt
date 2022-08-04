package com.itrocket.union.documentCreate.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.BaseTab
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStoreFactory
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
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
import com.itrocket.utils.disabledHorizontalPointerInputScroll
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DocumentCreateScreen(
    state: DocumentCreateStore.State,
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
    onReserveClickListener: (ReservesDomain) -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.inventory_params),
            screen = {
                ParamContent(
                    onParamClickListener = onParamClickListener,
                    params = state.params,
                    onCrossClickListener = onParamCrossClickListener,
                    onSaveClickListener = onSaveClickListener,
                    documentStatus = state.document.documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.document_create_accounting_object),
            screen = {
                AccountingObjectScreen(
                    isLoading = state.isLoading,
                    accountingObjectList = state.accountingObjects,
                    onAccountingObjectClickListener = {},
                    onSaveClickListener = onSaveClickListener,
                    onSettingsClickListener = onSettingsClickListener,
                    onChooseClickListener = onChooseAccountingObjectClickListener,
                    documentStatus = state.document.documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.document_create_reserves),
            screen = {
                ReservesScreen(
                    isLoading = state.isLoading,
                    reserves = state.reserves,
                    onReservesClickListener = onReserveClickListener,
                    onSaveClickListener = onSaveClickListener,
                    onSettingsClickListener = onSettingsClickListener,
                    onChooseClickListener = onChooseReserveClickListener,
                    documentStatus = state.document.documentStatus,
                    onConductClickListener = onConductClickListener
                )
            }
        )
    )

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    documentType = state.document.documentType,
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener
                )
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
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
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(
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
private fun ParamContent(
    params: List<ParamDomain>,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus,
    onParamClickListener: (ParamDomain) -> Unit,
    onCrossClickListener: (ParamDomain) -> Unit,
    onSaveClickListener: () -> Unit
) {
    Scaffold(bottomBar = {
        ParamBottomBar(
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
                        clickable = documentStatus != DocumentStatus.COMPLETED,
                        onFieldClickListener = {
                            onParamClickListener(it)
                        },
                        isCrossVisible = documentStatus != DocumentStatus.COMPLETED,
                        onCrossClickListener = {
                            onCrossClickListener(it)
                        }
                    )
                } else {
                    UnselectedBaseField(
                        label = stringResource(it.type.titleId),
                        onFieldClickListener = {
                            onParamClickListener(it)
                        },
                        clickable = documentStatus != DocumentStatus.COMPLETED
                    )
                }
            }
        }
    })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AccountingObjectScreen(
    isLoading: Boolean,
    accountingObjectList: List<AccountingObjectDomain>,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    onSettingsClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onChooseClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus
) {
    Scaffold(
        bottomBar = {
            ListBottomBar(
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
                    Empty(paddingValues = PaddingValues())
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
                                status = item.status?.type,
                                isEnabled = documentStatus != DocumentStatus.COMPLETED
                            )
                        }
                    }
                }
            }
        })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ReservesScreen(
    isLoading: Boolean,
    reserves: List<ReservesDomain>,
    onReservesClickListener: (ReservesDomain) -> Unit,
    onSettingsClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onChooseClickListener: () -> Unit,
    onConductClickListener: () -> Unit,
    documentStatus: DocumentStatus
) {
    Scaffold(
        bottomBar = {
            ListBottomBar(
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
                    Empty(paddingValues = PaddingValues())
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
                                isShowBottomLine = isShowBottomLine,
                                clickable = documentStatus != DocumentStatus.COMPLETED
                            )
                        }
                    }
                }
            }
        })
}

@Composable
private fun Toolbar(
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
private fun Empty(paddingValues: PaddingValues) {
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
private fun ParamBottomBar(
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
private fun ListBottomBar(
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
fun DocumentCreateScreenPreview() {
    DocumentCreateScreen(
        DocumentCreateStore.State(
            document = DocumentDomain(
                number = "1234543",
                documentStatus = DocumentStatus.CREATED,
                documentType = DocumentTypeDomain.WRITE_OFF,
                creationDate = 123213213,
                accountingObjects = listOf(),
                params = listOf(
                    ParamDomain(
                        "1", "fsdsfsdf",
                        type = ManualType.ORGANIZATION
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
                documentStatusId = "d1",
                userInserted = "",
                userUpdated = ""
            ),
            accountingObjects = listOf(),
            params = listOf(
                ParamDomain(
                    "1", "fsdsfsdf",
                    type = ManualType.ORGANIZATION
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
        {}
    )
}