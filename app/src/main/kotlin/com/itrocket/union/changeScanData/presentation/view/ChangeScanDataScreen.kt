package com.itrocket.union.changeScanData.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.changeScanData.domain.entity.ChangeScanType
import com.itrocket.union.changeScanData.presentation.store.ChangeScanDataStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.ImageButton
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.burntSienna
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.white
import com.itrocket.union.utils.ifBlankOrNullComposable

@Composable
fun ChangeScanDataScreen(
    state: ChangeScanDataStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onApplyClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
    onReaderPowerClickListener: () -> Unit,
    onScanDataTextChanged: (String) -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = appInsets.bottomInset.dp,
                    top = appInsets.topInset.dp
                )
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Toolbar(onCrossClick = onBackClickListener, changeScanType = state.changeScanType)
                if (state.changeScanType != ChangeScanType.RFID) {
                    MediumSpacer()
                    ScanTextField(
                        scanData = state.newScanValue,
                        onScanTextChanged = onScanDataTextChanged,
                        type = state.changeScanType
                    )
                    MediumSpacer()
                }
                Content(
                    currentScanValue = state.currentScanValue,
                    newScanValue = state.newScanValue,
                    changeScanType = state.changeScanType,
                    isSuccess = state.isApplyEnabled,
                    isScanDataExistError = state.isScanDataExistError
                )
                BottomBar(
                    isApplyEnabled = state.isApplyEnabled,
                    onApplyClickListener = onApplyClickListener,
                    onCancelClickListener = onCancelClickListener,
                    onReaderPowerClickListener = onReaderPowerClickListener,
                    isRfid = state.changeScanType == ChangeScanType.RFID
                )
            }
        }

    }
}

@Composable
private fun Toolbar(onCrossClick: () -> Unit, changeScanType: ChangeScanType) {
    Column(
        modifier = Modifier
            .background(
                white,
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 20.dp)
        ) {
            Text(
                text = stringResource(changeScanType.titleId),
                style = AppTheme.typography.h6,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_cross),
                contentDescription = null,
                colorFilter = ColorFilter.tint(graphite4),
                modifier = Modifier.clickable(
                    onClick = onCrossClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (changeScanType == ChangeScanType.RFID) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(graphite2)
            )
        }
    }
}

@Composable
private fun ScanTextField(
    scanData: String,
    onScanTextChanged: (String) -> Unit,
    type: ChangeScanType
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val mainColor = AppTheme.colors.mainColor
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(vertical = 4.dp)
    ) {
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
            text = scanData,
            hint = stringResource(type.hintId),
            hintStyle = AppTheme.typography.body1,
            hintColor = AppTheme.colors.secondaryColor,
            textStyle = AppTheme.typography.body1,
            onTextChanged = onScanTextChanged,
            focusRequester = focusRequester,
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(true)
            }),
            onFocusChanged = {
                underlineColor = if (it.hasFocus) {
                    mainColor
                } else {
                    brightGray
                }
            },
            underlineColor = underlineColor
        )
    }
}

@Composable
private fun Content(
    currentScanValue: String?,
    newScanValue: String,
    changeScanType: ChangeScanType,
    isSuccess: Boolean,
    isScanDataExistError: Boolean
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                ChangeBarcodeItem(
                    subtitle = stringResource(changeScanType.fromChangeId),
                    title = currentScanValue.ifBlankOrNullComposable {
                        stringResource(changeScanType.emptyValueId)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(40.dp))
                ChangeBarcodeItem(
                    subtitle = stringResource(changeScanType.toChangeId),
                    title = newScanValue.ifBlank { stringResource(id = R.string.change_scan_data_press_cursor) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_rounded_arrows),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                modifier = Modifier
                    .size(48.dp)
                    .shadow(20.dp, CircleShape)
                    .background(white, CircleShape)
                    .padding(horizontal = 10.dp, vertical = 11.dp)
            )
        }
        InfoBlock(
            changeScanType = changeScanType,
            isSuccess = isSuccess,
            isScanDataExistError = isScanDataExistError
        )
    }
}

@Composable
private fun InfoBlock(
    changeScanType: ChangeScanType,
    isSuccess: Boolean,
    isScanDataExistError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))
        when {
            isSuccess -> Text(
                text = stringResource(changeScanType.successLabelId),
                style = AppTheme.typography.h6,
                color = green7
            )
            isScanDataExistError -> Text(
                text = stringResource(changeScanType.errorLabelId),
                style = AppTheme.typography.h6,
                color = burntSienna
            )
        }
    }

}

@Composable
fun ChangeBarcodeItem(
    subtitle: String,
    title: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .background(AppTheme.colors.appBarBackgroundColor, RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = subtitle, style = AppTheme.typography.caption, color = white)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, style = AppTheme.typography.h6, color = white)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun BottomBar(
    isApplyEnabled: Boolean,
    onApplyClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
    onReaderPowerClickListener: () -> Unit,
    isRfid: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isRfid) {
            ImageButton(
                imageId = R.drawable.ic_settings,
                paddings = PaddingValues(12.dp),
                onClick = onReaderPowerClickListener,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            text = stringResource(R.string.common_cancel),
            onClick = onCancelClickListener
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(
            text = stringResource(R.string.common_apply),
            enabled = isApplyEnabled,
            onClick = onApplyClickListener
        )
    }
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
fun ChangeScanDataScreenPreview() {
    ChangeScanDataScreen(
        ChangeScanDataStore.State(changeScanType = ChangeScanType.RFID),
        AppInsets(),
        {},
        {},
        {},
        {},
        {}
    )
}