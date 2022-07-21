package com.itrocket.union.authContainer.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.authContainer.domain.entity.AuthContainerStep
import com.itrocket.union.authContainer.presentation.store.AuthContainerStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.StepBottomBar

@Composable
fun AuthScreen(
    state: AuthContainerStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onNextClickListener: () -> Unit
) {
    val step = state.currentStep.ordinal + 1
    val stepCount = AuthContainerStep.values().size

    AppTheme {
        StepBottomBar(
            step = step,
            stepCount = stepCount,
            stepText = stringResource(
                id = R.string.common_step,
                step.toString()
            ),
            isLoading = state.isLoading,
            buttonText = stringResource(R.string.common_next),
            btnLastStepText = stringResource(R.string.auth_sign_in),
            onBackClickListener = onBackClickListener,
            onNextClickListener = onNextClickListener,
            isButtonNextEnabled = state.isEnable
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
fun AuthScreenPreview() {
    AuthScreen(AuthContainerStore.State(), AppInsets(), {}, {})
}