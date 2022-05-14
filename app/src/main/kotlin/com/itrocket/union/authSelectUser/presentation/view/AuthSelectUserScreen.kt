package com.itrocket.union.authSelectUser.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.itrocket.union.authSelectUser.presentation.store.AuthSelectUserStore
import com.itrocket.union.filter.domain.entity.FilterValueType
import com.itrocket.union.ui.BottomSheetContainer
import com.itrocket.union.ui.DefaultEditText
import com.itrocket.union.ui.Loader
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.RadioButtonField
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.TwoTextButtonBottomBar
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun AuthSelectUserScreen(
    state: AuthSelectUserStore.State,
    appInsets: AppInsets,
    onUserSearchTextChanged: (String) -> Unit,
    onUserSelected: (String) -> Unit,
    onCrossClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit,
) {
    AppTheme {
        val focusRequest = remember {
            FocusRequester()
        }
        BottomSheetContainer(appInsets = appInsets) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.auth_select_user_title),
                    style = AppTheme.typography.h6
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onCrossClickListener),
                    colorFilter = ColorFilter.tint(graphite4)
                )
            }
            MediumSpacer()
            DefaultEditText(
                text = state.searchText,
                hint = stringResource(R.string.auth_select_user_hint),
                focusRequester = focusRequest,
                onSearchTextChanged = onUserSearchTextChanged
            )
            MediumSpacer()
            ListContent(
                isLoading = state.isLoading,
                userList = state.userList,
                selectedUser = state.selectedUser,
                onUserSelected = onUserSelected
            )
            Spacer(modifier = Modifier.height(24.dp))
            TwoTextButtonBottomBar(
                firstButtonText = stringResource(R.string.common_cancel),
                secondButtonText = stringResource(R.string.common_apply),
                onFirstButtonClickListener = onCancelClickListener,
                onSecondButtonClickListener = onAcceptClickListener
            )
        }
    }
}

@Composable
private fun ListContent(
    isLoading: Boolean,
    userList: List<String>,
    selectedUser: String,
    onUserSelected: (String) -> Unit
) {
    if (isLoading) {
        Column(
            modifier = Modifier.height(144.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(userList) {
                RadioButtonField(
                    label = it,
                    onFieldClickListener = {
                        onUserSelected(it)
                    },
                    isSelected = it == selectedUser
                )
            }
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
fun AuthSelectUserScreenPreview() {
    AuthSelectUserScreen(AuthSelectUserStore.State(
        userList = listOf("roman", "test")
    ), AppInsets(), {}, {}, {}, {}, {})
}