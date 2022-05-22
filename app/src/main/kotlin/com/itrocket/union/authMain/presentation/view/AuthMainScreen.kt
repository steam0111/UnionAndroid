package com.itrocket.union.authMain.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.authMain.presentation.store.AuthMainStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.AuthContent
import com.itrocket.union.ui.AuthEditText
import com.itrocket.union.ui.ButtonWithLoader
import com.itrocket.union.ui.OutlinedButton
import com.itrocket.union.ui.psb3
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.white

@Composable
fun AuthMainScreen(
    state: AuthMainStore.State,
    appInsets: AppInsets,
    onSignInClickListener: () -> Unit,
    onUserChangeClickListener: () -> Unit,
    onDatabaseSettingsClickListener: () -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordVisibilityClickListener: () -> Unit
) {
    AppTheme {
        val focusRequest = remember {
            FocusRequester()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp)
        ) {
            AuthContent(
                title = stringResource(R.string.auth_main_title, state.login),
                subtitle = stringResource(R.string.auth_main_subtitle),
                fields = listOf {
                    AuthEditText(
                        text = state.password,
                        hint = stringResource(id = R.string.auth_user_password),
                        onTextChanged = onPasswordChanged,
                        focusRequester = focusRequest,
                        isShowVisibilityButton = true,
                        isTextVisible = state.isPasswordVisible,
                        onVisibilityClickListener = onPasswordVisibilityClickListener
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonWithLoader(
                onClick = onSignInClickListener,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                isEnabled = state.enabled,
                isLoading = state.isLoading,
                content = {
                    Text(
                        text = stringResource(R.string.common_sign_in),
                        style = AppTheme.typography.body2,
                        color = white,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                text = stringResource(R.string.auth_main_user_change),
                onClick = onUserChangeClickListener,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                text = stringResource(R.string.auth_main_database_settings),
                onClick = onDatabaseSettingsClickListener,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                textColor = psb3,
                outlineColor = psb4
            )

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
fun AuthMainScreenPreview() {
    AuthMainScreen(
        AuthMainStore.State(login = "Roman", password = "123321"),
        AppInsets(previewTopInsetDp),
        {},
        {},
        {},
        {},
        {}
    )
}