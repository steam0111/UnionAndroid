package com.itrocket.union.authUser.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.authUser.presentation.store.AuthUserStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.AuthContent
import com.itrocket.union.ui.AuthEditText
import com.itrocket.union.ui.BaseCheckbox

@Composable
fun AuthUserScreen(
    state: AuthUserStore.State,
    appInsets: AppInsets,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordVisibilityClickListener: () -> Unit,
    onActiveDirectoryChanged: () -> Unit
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
                        isShowVisibilityButton = true,
                        isTextVisible = state.isPasswordVisible,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onVisibilityClickListener = onPasswordVisibilityClickListener
                    )
                },
                {
                    Row(
                        modifier = Modifier.padding(bottom = 16.dp).clickable(
                            onClick = onActiveDirectoryChanged,
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BaseCheckbox(
                            isChecked = state.isActiveDirectory,
                            onCheckClickListener = onActiveDirectoryChanged
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.auth_active_directory))
                    }
                }
            ),
            medias = state.medias
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
    Column(modifier = Modifier.padding(top = previewTopInsetDp.dp)) {
        AuthUserScreen(AuthUserStore.State(), AppInsets(), {}, {}, {}, {})
    }
}