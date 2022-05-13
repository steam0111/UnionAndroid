package com.itrocket.union.authUser.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.AuthContent
import com.itrocket.union.ui.AuthEditText

@Composable
fun AuthUserScreen(
    state: AuthUserStore.State,
    appInsets: AppInsets,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {
    AppTheme {

        val focusRequest = remember {
            FocusRequester()
        }
        AuthContent(
            title = stringResource(R.string.auth_user_title),
            subtitle = stringResource(R.string.auth_suggest),
            fields = listOf(
                {
                    AuthEditText(
                        text = state.login,
                        hint = stringResource(id = R.string.auth_user_login),
                        onTextChanged = onLoginChanged,
                        focusRequester = focusRequest
                    )
                },
                {
                    AuthEditText(
                        text = state.password,
                        hint = stringResource(id = R.string.auth_user_password),
                        onTextChanged = onPasswordChanged,
                        focusRequester = focusRequest,
                        visualTransformation = PasswordVisualTransformation()
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
fun AuthUserScreenPreview() {
    AuthUserScreen(AuthUserStore.State(), AppInsets(), {}, {})
}