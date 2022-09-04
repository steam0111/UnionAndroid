package com.itrocket.union.nfcReader.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.nfcReader.domain.entity.NfcReaderState
import com.itrocket.union.nfcReader.domain.entity.NfcReaderType
import com.itrocket.union.nfcReader.presentation.store.NfcReaderStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.red5
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun NfcReaderScreen(
    state: NfcReaderStore.State,
    appInsets: AppInsets,
    onOkClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
) {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = appInsets.bottomInset.dp,
                    top = appInsets.topInset.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(
                        top = 16.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(state.nfcReaderType.titleId),
                        style = AppTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier.clickableUnbounded(onClick = onCancelClickListener),
                        colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(psb4)
                )
                Spacer(modifier = Modifier.height(24.dp))
                when (state.nfcReaderState) {
                    is NfcReaderState.Error -> ErrorContent(error = stringResource(state.nfcReaderState.errorId))
                    NfcReaderState.Success -> SuccessContent()
                    NfcReaderState.Waiting -> WaitingContent()
                }
                Spacer(modifier = Modifier.height(24.dp))
                BottomBar(
                    isOkEnabled = state.nfcReaderState == NfcReaderState.Success,
                    onOkClickListener = onOkClickListener,
                    onCancelClickListener = onCancelClickListener
                )
            }
        }
    }
}

@Composable
private fun WaitingContent() {
    Info(
        textStyle = TextStyle(
            color = AppTheme.colors.mainTextColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        imageSize = 64.dp
    )
}

@Composable
private fun ErrorContent(error: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = error, style = AppTheme.typography.h6, color = red5)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = stringResource(R.string.nfc_reader_repeat), style = AppTheme.typography.body1)
        Spacer(modifier = Modifier.height(40.dp))
        Info(
            textStyle = TextStyle(
                color = AppTheme.colors.secondaryColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            imageSize = 40.dp
        )
    }
}

@Composable
private fun SuccessContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.nfc_reader_success),
            style = AppTheme.typography.h6,
            color = green7
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(R.string.nfc_reader_document_conduct_success),
            style = AppTheme.typography.body1
        )
    }
}

@Composable
private fun Info(textStyle: TextStyle, imageSize: Dp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_nfc),
            contentDescription = null,
            modifier = Modifier.size(imageSize),
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(R.string.nfc_reader_description),
            style = textStyle
        )
    }
}

@Composable
private fun BottomBar(
    isOkEnabled: Boolean,
    onOkClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        if (!isOkEnabled) {
            TextButton(
                text = stringResource(R.string.common_cancel),
                onClick = onCancelClickListener
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        TextButton(
            text = stringResource(R.string.common_ok),
            enabled = isOkEnabled,
            onClick = onOkClickListener
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
fun NfcReaderScreenPreview() {
    NfcReaderScreen(
        NfcReaderStore.State(
            nfcReaderType = NfcReaderType.DocumentConduct(
                DocumentDomain(
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
                )
            ),
        ),
        AppInsets(),
        {},
        {},
    )
}