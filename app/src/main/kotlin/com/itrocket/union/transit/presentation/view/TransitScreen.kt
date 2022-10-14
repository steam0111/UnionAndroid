package com.itrocket.union.transit.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.itrocket.core.base.AppInsets
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.transit.presentation.store.TransitStore
import com.itrocket.union.ui.documents.DocumentCreateBaseScreen
import com.itrocket.union.ui.documents.DocumentCreateBaseScreenPreview

@Composable
fun TransitScreen(
    state: TransitStore.State,
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
        params = state.params,
        selectedPage = state.selectedPage,
        documentStatus = state.transit.documentStatus,
        accountingObjectList = state.accountingObjects,
        isLoading = state.isLoading,
        reserves = state.reserves,
        documentType = state.transit.documentType,
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
        confirmDialogType = state.confirmDialogType,
        isDocumentExist = state.transit.isDocumentExists,
        isDocumentChangePermitted = if (state.transit.isDocumentExists) {
            state.canUpdate
        } else {
            state.canCreate
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
fun TransitScreenPreview() {
    DocumentCreateBaseScreenPreview()
}