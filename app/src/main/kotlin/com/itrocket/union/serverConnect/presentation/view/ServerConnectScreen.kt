package com.itrocket.union.serverConnect.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.serverConnect.presentation.store.ServerConnectStore
import com.itrocket.union.ui.AuthContent
import com.itrocket.union.ui.AuthEditText

@Composable
fun ServerConnectScreen(
    state: ServerConnectStore.State,
    appInsets: AppInsets,
    onServerAddressChanged: (String) -> Unit,
    onPortChanged: (String) -> Unit
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
                        onTextChanged = onPortChanged,
                        focusRequester = focusRequest
                    )
                }
            )
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
fun ServerConnectScreenPreview() {
    ServerConnectScreen(ServerConnectStore.State(), AppInsets(), {}, {})
}