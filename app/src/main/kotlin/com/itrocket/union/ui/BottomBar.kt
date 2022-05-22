package com.itrocket.union.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R

private const val MIN_STEP = 1

@Composable
fun ButtonBottomBar(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = text,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StepBottomBar(
    step: Int,
    stepCount: Int,
    stepText: String,
    isButtonNextEnabled: Boolean,
    buttonText: String,
    btnLastStepText: String,
    isLoading: Boolean = false,
    onBackClickListener: () -> Unit,
    onNextClickListener: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(vertical = 16.dp,horizontal = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IndicatorWithText(
            text = stepText.uppercase(),
            step = step,
            stepCount = stepCount,
            modifier = Modifier.size(67.dp, 4.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        OutlinedImageButton(
            imageId = R.drawable.ic_arrow_back,
            onClick = onBackClickListener,
            enabled = step > MIN_STEP,
            modifier = Modifier,
            paddingValues = PaddingValues(12.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ButtonWithLoader(
            content = {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 24.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (step == stepCount) {
                            btnLastStepText.uppercase()
                        } else {
                            buttonText.uppercase()
                        },
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.body2,
                        fontWeight = FontWeight.Medium,
                        color = white
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                    )
                }
            },
            isEnabled = isButtonNextEnabled,
            onClick = onNextClickListener,
            modifier = Modifier.weight(1f),
            isLoading = isLoading
        )
    }
}

@Composable
@Preview
private fun StepBottomBarPreview() {
    StepBottomBar(
        step = 1,
        stepCount = 3,
        stepText = "ШАГ 1",
        buttonText = "Далее",
        btnLastStepText = "Войти",
        onBackClickListener = { /*TODO*/ },
        isButtonNextEnabled = true
    ) {

    }
}