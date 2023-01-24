package com.itrocket.union.serverConnect.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.AuthContent
import com.itrocket.union.ui.AuthEditText
import com.itrocket.union.ui.ChooseAlertDialog

@Composable
fun ServerConnectScreen(
    state: ServerConnectStore.State,
    appInsets: AppInsets,
    onServerAddressChanged: (String) -> Unit,
    onPortChanged: (String) -> Unit,
    onOpenFileClick: () -> Unit,
    onCreateFileClick: () -> Unit
) {
    AppTheme {
        val focusRequest = remember {
            FocusRequester()
        }
        AuthContent(
            title = stringResource(R.string.connect_to_server_title),
            subtitle = stringResource(R.string.auth_suggest),
            fields = listOf(
                {
                    AuthEditText(
                        text = state.serverAddress,
                        hint = stringResource(id = R.string.connect_to_server_address),
                        onTextChanged = onServerAddressChanged,
                        focusRequester = focusRequest
                    )
                },
                {
                    AuthEditText(
                        text = state.port,
                        hint = stringResource(id = R.string.connect_to_server_port),
                        onTextChanged = {
                            if (it.isDigitsOnly()) {
                                onPortChanged(it)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        focusRequester = focusRequest
                    )
                }
            ),
            medias = state.medias
        )
        when (state.dialogType) {
            AlertType.APP_DATA_FILE -> ChooseAlertDialog(
                textRes = R.string.app_file_data_description,
                actionResources = listOf(
                    R.string.app_file_data_open,
                    R.string.app_file_data_create
                ),
                onActionClick = { position ->
                    when (position) {
                        R.string.app_file_data_open -> {
                            onOpenFileClick()
                        }
                        R.string.app_file_data_create -> {
                            onCreateFileClick()
                        }
                    }
                }
            )
            else -> {}
        }
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
fun ServerConnectScreenPreview() {
    Column(modifier = Modifier.padding(top = previewTopInsetDp.dp)) {
        ServerConnectScreen(ServerConnectStore.State(), AppInsets(), {}, {}, {}, {})
    }
}