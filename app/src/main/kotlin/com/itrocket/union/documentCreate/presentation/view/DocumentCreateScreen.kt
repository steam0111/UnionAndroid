package com.itrocket.union.documentCreate.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.documentCreate.presentation.store.DocumentCreateStore
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.reserves.domain.entity.ReservesDomain
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
    onDismissConfirmDialog: () -> Unit,
    onDeleteAccountingObjectClickListener: (String) -> Unit,
    onDeleteReserveClickListener: (String) -> Unit,
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
        onDismissConfirmDialog = onDismissConfirmDialog,
        isDocumentExist = state.document.isDocumentExists,
        isDocumentChangePermitted = if (state.document.isDocumentExists) {
            state.canUpdate
        } else {
            state.canCreate
        },
        canDelete = state.canDelete,
        onDeleteAccountingObjectClickListener = onDeleteAccountingObjectClickListener,
        onDeleteReserveClickListener = onDeleteReserveClickListener
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
            readingModeTab = ReadingModeTab.SN
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
    )
}

enum class DocumentConfirmAlertType {
    CONDUCT,
    SAVE,
    NONE
}