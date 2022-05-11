package com.itrocket.union.auth.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.EditText
import com.itrocket.union.auth.domain.entity.AuthStep
import com.itrocket.union.auth.presentation.store.AuthStore
import com.itrocket.union.ui.StepBottomBar
import com.itrocket.union.ui.blue6
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb3
import com.itrocket.union.ui.psb6

@Composable
fun AuthScreen(
    state: AuthStore.State,
    appInsets: AppInsets,
    onPrevClickListener: () -> Unit,
    onNextClickListener: () -> Unit
) {
    val step = state.currentStep.stepNumber
    val stepCount = AuthStep.values().size

    AppTheme {
        StepBottomBar(
            step = step,
            stepCount = stepCount,
            stepText = stringResource(
                id = R.string.common_step,
                step.toString()
            ),
            btnText = stringResource(R.string.common_next),
            btnLastStepText = stringResource(R.string.auth_sign_in),
            onPrevClickListener = onPrevClickListener,
            onNextClickListener = onNextClickListener
        )
    }
}

@Composable
private fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(R.drawable.ic_logo_small), contentDescription = null)
    }
}

@Composable
fun AuthEditText(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    focusRequester: FocusRequester
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    EditText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = text,
        hint = hint,
        textStyle = AppTheme.typography.body1,
        hintStyle = AppTheme.typography.body1,
        hintColor = psb3,
        onTextChanged = onTextChanged,
        focusRequester = focusRequester,
        onFocusChanged = {
            underlineColor = if (it.hasFocus) {
                psb6
            } else {
                psb1
            }
        },
        underlineColor = underlineColor
    )
}

@Composable
fun AuthContent(title: String, subtitle: String, fields: List<@Composable () -> Unit>) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 48.dp)
    ) {

        TopBar()
        Spacer(Modifier.height(8.dp))
        Text(text = title, style = AppTheme.typography.h5, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(text = subtitle, style = AppTheme.typography.body2, color = psb6)
        Spacer(Modifier.height(24.dp))
        LazyColumn {
            items(fields) {
                it()
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
fun AuthScreenPreview() {
    AuthScreen(AuthStore.State(), AppInsets(), {}, {})
}