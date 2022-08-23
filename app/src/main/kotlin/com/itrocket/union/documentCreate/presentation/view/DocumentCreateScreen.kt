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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.ui.ConfirmAlertDialog
import com.itrocket.union.ui.documents.DocumentCreateBaseScreen

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
    onReserveClickListener: (ReservesDomain) -> Unit,
    onConfirmActionClick: () -> Unit,
    onDismissConfirmDialog: () -> Unit
) {
    DocumentCreateBaseScreen(
        confirmDialogType = state.confirmDialogType,
        params = state.params,
        selectedPage = state.selectedPage,
        documentStatus = state.document.documentStatus,
        accountingObjectList = state.accountingObjects,
        isLoading = state.isLoading,
        reserves = state.reserves,
        documentType = state.document.documentType,
        appInsets = appInsets,
        onBackClickListener = onBackClickListener,
        onDropClickListener = onDropClickListener,
        onSaveClickListener = onSaveClickListener,
        onPageChanged = onPageChanged,
        onParamClickListener = onParamClickListener,
        onParamCrossClickListener = onParamCrossClickListener,
        onSettingsClickListener = onSettingsClickListener,
        onChooseAccountingObjectClickListener = onChooseAccountingObjectClickListener,
        onChooseReserveClickListener = onChooseReserveClickListener,
        onConductClickListener = onConductClickListener,
        onReserveClickListener = onReserveClickListener,
        onConfirmActionClick = onConfirmActionClick,
        onDismissConfirmDialog = onDismissConfirmDialog
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
                    StructuralParamDomain(manualType = ManualType.STRUCTURAL_FROM),
                    StructuralParamDomain(manualType = ManualType.STRUCTURAL_TO),
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
                StructuralParamDomain(manualType = ManualType.STRUCTURAL_FROM),
                StructuralParamDomain(manualType = ManualType.STRUCTURAL_TO),
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
        {},
        {},
        {})
}

enum class DocumentConfirmAlertType {
    CONDUCT,
    SAVE,
    NONE
}